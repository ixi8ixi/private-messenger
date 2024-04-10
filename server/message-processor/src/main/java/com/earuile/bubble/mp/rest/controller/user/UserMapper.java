package com.earuile.bubble.mp.rest.controller.user;

import com.earuile.bubble.mp.public_interface.user.chat.dto.UserGetChatsRequestDto;
import com.earuile.bubble.mp.public_interface.user.chat.dto.UserGetChatsResponseDto;
import com.earuile.bubble.mp.public_interface.user.registration.dto.UserRegistrationRequestDto;
import com.earuile.bubble.mp.public_interface.user.registration.dto.UserRegistrationResponseDto;
import com.earuile.bubble.mp.rest.controller.ValidationService;
import com.earuile.bubble.mp.rest.content.ChatInfo;
import com.earuile.bubble.mp.rest.content.UserInfo;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.chat.UserGetChatsRequest;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.chat.UserGetChatsResponse;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.register.UserRegistrationRequest;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.register.UserRegistrationResponse;
import com.earuile.bubble.mp.rest.controller.user.validation.UserValidation;
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
        validationService.validateUserId(request.userId());

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
                                .users(chatInfoDto.users()
                                        .stream()
                                        .map(userInfoDto -> UserInfo.builder()
                                                .id(userInfoDto.id())
                                                .login(userInfoDto.login())
                                                .name(userInfoDto.name())
                                                .build())
                                        .toList())
                                .time(chatInfoDto.time().toEpochSecond(ZoneOffset.UTC))
                                .build())
                        .toList())
                .time(System.currentTimeMillis())
                .build();
    }

}
