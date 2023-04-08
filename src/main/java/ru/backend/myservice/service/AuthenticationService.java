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

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        UserDto userByEmail = userService.getUserByEmail(authenticationRequest.getEmail());
        String jwtToken = jwtService.generateToken(new UserDetailsImpl(userByEmail)); // TODO: factory pattern ?

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
