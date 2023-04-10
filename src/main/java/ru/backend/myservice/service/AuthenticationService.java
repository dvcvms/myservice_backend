package ru.backend.myservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.backend.myservice.dto.UserDto;
import ru.backend.myservice.model.AuthenticationRequest;
import ru.backend.myservice.model.AuthenticationResponse;
import ru.backend.myservice.security.UserDetailsImpl;
import ru.backend.myservice.service.impl.UserServiceImpl;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDto user = userService.getUserByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(new UserDetailsImpl(user));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
