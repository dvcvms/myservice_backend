package ru.backend.myservice.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.backend.myservice.dto.UserDto;
import ru.backend.myservice.exception.UserNotValidException;
import ru.backend.myservice.service.impl.UserServiceImpl;

@Service
@RequiredArgsConstructor
public class UserValidator {

    private final UserServiceImpl userService;

    public void validate(UserDto user) {
        checkEmail(user.getEmail());
        checkUsername(user.getUsername());
    }

    private void checkEmail(String email) {
        if (userService.existsByEmail(email)) {
            throw new UserNotValidException(String.format("User with `%s` email already exists.", email));
        }
    }

    private void checkUsername(String username) {
        if (userService.existsByUsername(username)) {
            throw new UserNotValidException(String.format("User with `%s` username already exists.", username));
        }
    }
}
