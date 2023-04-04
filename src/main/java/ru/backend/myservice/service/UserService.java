package ru.backend.myservice.service;

import ru.backend.myservice.dto.RoleDto;
import ru.backend.myservice.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long id);

    UserDto getUserByUsername(String username);

    UserDto getUserByEmail(String email);

    List<UserDto> getAllUsers();

    UserDto updateUser(UserDto userDto);

    String deleteUser(Long id);

    UserDto addRole(UserDto user, RoleDto role);
}
