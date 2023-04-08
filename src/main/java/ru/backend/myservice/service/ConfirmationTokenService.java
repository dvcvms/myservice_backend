package ru.backend.myservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.backend.myservice.dto.UserDto;
import ru.backend.myservice.entity.ConfirmationTokenEntity;
import ru.backend.myservice.mapper.UserMapper;
import ru.backend.myservice.repository.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final UserMapper userMapper;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationTokenEntity token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationTokenEntity> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public ConfirmationTokenEntity generateToken(String token, UserDto user) {
        return ConfirmationTokenEntity.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(userMapper.toEntity(user))
                .build();
    }
}
