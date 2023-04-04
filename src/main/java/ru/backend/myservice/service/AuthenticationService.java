package ru.backend.myservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.backend.myservice.dto.RoleDto;
import ru.backend.myservice.dto.UserDto;
import ru.backend.myservice.model.AuthenticationRequest;
import ru.backend.myservice.model.AuthenticationResponse;
import ru.backend.myservice.model.RegisterRequest;
import ru.backend.myservice.security.UserDetailsImpl;
import ru.backend.myservice.service.impl.RoleServiceImpl;
import ru.backend.myservice.service.impl.UserServiceImpl;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest) {

        UserDto user = UserDto.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        RoleDto role = roleService.findByName("ROLE_USER");
        userService.createUser(user);
        UserDto updatedUser = userService.addRole(user, role);
        String jwtToken = jwtService.generateToken(new UserDetailsImpl(updatedUser));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        UserDto userByEmail = userService.getUserByEmail(authenticationRequest.getEmail());
        String jwtToken = jwtService.generateToken(new UserDetailsImpl(userByEmail));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
