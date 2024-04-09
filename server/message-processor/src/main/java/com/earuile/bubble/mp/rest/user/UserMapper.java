package com.earuile.bubble.mp.rest.user;

import com.earuile.bubble.mp.public_interface.user.chat.UserGetChatsRequestDto;
import com.earuile.bubble.mp.public_interface.user.chat.UserGetChatsResponseDto;
import com.earuile.bubble.mp.public_interface.user.registration.UserRegistrationRequestDto;
import com.earuile.bubble.mp.public_interface.user.registration.UserRegistrationResponseDto;
import com.earuile.bubble.mp.rest.ValidationService;
import com.earuile.bubble.mp.rest.user.info.content.ChatInfo;
import com.earuile.bubble.mp.rest.user.info.end_point.chat.UserGetChatsRequest;
import com.earuile.bubble.mp.rest.user.info.end_point.chat.UserGetChatsResponse;
import com.earuile.bubble.mp.rest.user.registration.info.end_point.register.UserRegistrationRequest;
import com.earuile.bubble.mp.rest.user.registration.info.end_point.register.UserRegistrationResponse;
import com.earuile.bubble.mp.rest.user.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ValidationService validationService;
    private final UserValidation userValidation;

    public UserRegistrationRequestDto mapRequestToDto(UserRegistrationRequest request) {
        validationService.validateMaxInt(
                userValidation.maxLengthLogin,
                request.login().length(),
                "Login is very long.",
                "length"
        );
        validationService.validateMaxInt(
                userValidation.maxLengthPassword,
                request.password().length(),
                "Password is very long.",
                "length"
        );
        validationService.validateMaxInt(
                userValidation.maxLengthName,
                request.name().length(),
                "Name is very long.",
                "length"
        );

        return UserRegistrationRequestDto.builder()
                .login(request.login())
                .password(request.password())
                .name(request.name())
                .build();
    }

    public UserGetChatsRequestDto mapRequestToDto(UserGetChatsRequest request) {
        validationService.validateCorrectPrefixAndUUIDSuffix(userValidation.idPrefix, request.userId());

        return UserGetChatsRequestDto.builder()
                .userId(request.userId())
                .build();
    }

    public UserRegistrationResponse mapDtoToResponse(UserRegistrationResponseDto responseDto) {
        return UserRegistrationResponse.builder()
                .userId(responseDto.userId())
                .time(responseDto.time().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

    public UserGetChatsResponse mapDtoToResponse(UserGetChatsResponseDto responseDto) {
        return UserGetChatsResponse.builder()
                .chats(responseDto.chats()
                        .stream()
                        .map(chatInfoDto -> ChatInfo.builder()
                                .id(chatInfoDto.id())
                                .name(chatInfoDto.name())
                                .time(chatInfoDto.time().toEpochSecond(ZoneOffset.UTC))
                                .build())
                        .toList())
                .time(System.currentTimeMillis())
                .build();
    }

}
