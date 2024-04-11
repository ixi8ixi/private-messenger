package com.earuile.bubble.mp.rest.validation;

import com.earuile.bubble.mp.rest.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValidationService {
    @Value("${rest.user.validation.id-prefix}")
    private String userIdPrefix;
    @Value("${rest.chat.validation.id-prefix}")
    private String chatIdPrefix;

    public void validateUserId(String id) {
        validateCorrectPrefixAndUUIDSuffix(userIdPrefix, id);
    }

    public void validateChatId(String id) {
        validateCorrectPrefixAndUUIDSuffix(chatIdPrefix, id);
    }

    public void validateCorrectPrefixAndUUIDSuffix(String prefix, String str) {
        validateCorrectPrefix(prefix, str);
        validateCorrectUUIDSuffix(prefix.length(), str);
    }

    public void validateCorrectPrefix(String prefix, String str) {
        if (!str.startsWith(prefix)) {
            throw new ValidationException(
                    "Incorrect prefix.",
                    prefix,
                    str.substring(0, prefix.length())
            );
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void validateCorrectUUIDSuffix(int prefixLength, String str) {
        String suffix = str.substring(prefixLength);
        try {
            UUID.fromString(suffix);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(
                    "Incorrect uuid suffix",
                    "correct default uuid",
                    suffix
            );
        }
    }

    public void validateMaxInt(int maxExpected, int actual, String message, String name) {
        if (actual > maxExpected) {
            throw new ValidationException(
                    message,
                    "%s <= %d".formatted(name, maxExpected),
                    Integer.toString(actual));
        }
    }

}
