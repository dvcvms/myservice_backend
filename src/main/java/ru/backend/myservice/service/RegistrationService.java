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
import ru.backend.myservice.property.ServerProperties;
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
    private final ServerProperties serverProperties;

    @Transactional
    public RegistrationResponse register(RegistrationRequest request) {

        UserDto user = UserDto.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
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
        return String.format("http://%s:%s/registration/confirm?token=%s",
                serverProperties.getHost(),
                serverProperties.getPort(),
                jwtToken);
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
