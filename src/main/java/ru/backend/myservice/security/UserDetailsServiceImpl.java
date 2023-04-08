package ru.backend.myservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.backend.myservice.dto.UserDto;
import ru.backend.myservice.service.impl.UserServiceImpl;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserDto userIntoDatabase = userService.getUserByEmail(email);
        return new UserDetailsImpl(userIntoDatabase);
    }

    public int enableUser(String email) {
        return userService.enableUser(email);
    }
}