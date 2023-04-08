package ru.backend.myservice.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.backend.myservice.dto.RoleDto;
import ru.backend.myservice.dto.UserDto;
import ru.backend.myservice.entity.UserEntity;
import ru.backend.myservice.exception.UserNotFoundException;
import ru.backend.myservice.mapper.RoleMapper;
import ru.backend.myservice.mapper.UserMapper;
import ru.backend.myservice.repository.UserRepository;
import ru.backend.myservice.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity savedUser = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity userIntoDatabase = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDto(userIntoDatabase);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        UserEntity userIntoDatabase = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("username: " + username));
        return userMapper.toDto(userIntoDatabase);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userIntoDatabase = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("email: " + email));
        return userMapper.toDto(userIntoDatabase);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return null; // TODO    
    }

    @Override
    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);

        return String.format("User with id=%d has been deleted success.", id);
    }

    @Override
    public UserDto addRole(UserDto user, RoleDto role) {
        UserEntity userIntoDatabase = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("email: " + user.getEmail()));

        userIntoDatabase.addRole(roleMapper.toEntity(role));
        return userMapper.toDto(userRepository.save(userIntoDatabase));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public int enableUser(String email) {
        return userRepository.enableAppUser(email);
    }
}
