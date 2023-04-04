package ru.backend.myservice.security;

import ru.backend.myservice.dto.UserDto;
import ru.backend.myservice.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserDto userByEmail = userService.getUserByEmail(email);

        return new UserDetailsImpl(userByEmail);
    }
}