package ru.backend.myservice.validator;

import org.springframework.stereotype.Service;
import ru.backend.myservice.entity.ConfirmationTokenEntity;
import ru.backend.myservice.exception.ConfirmationTokenNotValidException;

import java.time.LocalDateTime;

@Service
public class ConfirmTokenValidator {

    public void validate(ConfirmationTokenEntity confirmationToken) {

        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmationTokenNotValidException("Email already confirmed.");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenNotValidException("Token expired.");
        }
    }
}
