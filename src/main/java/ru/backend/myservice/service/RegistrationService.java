package ru.backend.myservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.backend.myservice.dto.RoleDto;
import ru.backend.myservice.dto.UserDto;
import ru.backend.myservice.entity.ConfirmationTokenEntity;
import ru.backend.myservice.model.EmailBody;
import ru.backend.myservice.model.RegistrationRequest;
import ru.backend.myservice.model.RegistrationResponse;
import ru.backend.myservice.security.UserDetailsImpl;
import ru.backend.myservice.security.UserDetailsServiceImpl;
import ru.backend.myservice.service.impl.RoleServiceImpl;
import ru.backend.myservice.service.impl.UserServiceImpl;
import ru.backend.myservice.validator.ConfirmTokenValidator;
import ru.backend.myservice.validator.UserValidator;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final UserValidator userValidator;
    private final ConfirmTokenValidator confirmTokenValidator;
    private final UserDetailsServiceImpl userDetailsService;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ConfirmationTokenService confirmationTokenService;

    @Transactional
    public RegistrationResponse register(RegistrationRequest registrationRequest) {

        UserDto user = UserDto.builder() // TODO: do mapper?-
                .username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .firstname(registrationRequest.getFirstname())
                .lastname(registrationRequest.getLastname())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();

        userValidator.validate(user);

        UserDto createdUser = userService.createUser(user);
        RoleDto role = roleService.findByName("ROLE_USER");
        UserDto updatedUser = userService.addRole(createdUser, role);
        String jwtToken = jwtService.generateToken(new UserDetailsImpl(updatedUser));

        ConfirmationTokenEntity confirmationToken = confirmationTokenService.generateToken(jwtToken, updatedUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // Send verification link
        emailSenderService.sendEmail(generateEmailBody(updatedUser, jwtToken));

        return RegistrationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private EmailBody generateEmailBody(UserDto user, String jwtToken) {
        return EmailBody.builder()
                .toEmail(user.getEmail())
                .subject("Verification")
                .body(generateLink(jwtToken))
                .build();
    }

    private String generateLink(String jwtToken) {
        return String.format("http://localhost:8080/registration/confirm?token=%s", jwtToken); // TODO: localhost and port from properties
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationTokenEntity confirmationToken =
                confirmationTokenService.getToken(token)
                        .orElseThrow(() -> new IllegalStateException("token not found"));

        confirmTokenValidator.validate(confirmationToken);

        confirmationTokenService.setConfirmedAt(token);
        userDetailsService.enableUser(
                confirmationToken.getUser().getEmail());

        return "Email was confirmed successfully.";
    }
}
