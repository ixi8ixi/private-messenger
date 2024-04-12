package com.earuile.bubble.mp.rest.controller.user;

import com.earuile.bubble.mp.public_interface.user.chat.dto.UserGetChatsRequestDto;
import com.earuile.bubble.mp.public_interface.user.chat.dto.UserGetChatsResponseDto;
import com.earuile.bubble.mp.public_interface.user.registration.dto.UserRegistrationRequestDto;
import com.earuile.bubble.mp.public_interface.user.registration.dto.UserRegistrationResponseDto;
import com.earuile.bubble.mp.public_interface.user.user_info.GetUserInfoRequestDto;
import com.earuile.bubble.mp.public_interface.user.user_info.GetUserInfoResponseDto;
import com.earuile.bubble.mp.rest.content.ContentMapper;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.chat.UserGetChatsRequest;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.chat.UserGetChatsResponse;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.register.UserRegistrationRequest;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.register.UserRegistrationResponse;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.user_info.GetUserInfoRequest;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.user_info.GetUserInfoResponse;
import com.earuile.bubble.mp.rest.controller.user.validation.UserValidation;
import com.earuile.bubble.mp.rest.exception.ValidationException;
import com.earuile.bubble.mp.rest.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ValidationService validationService;
    private final UserValidation userValidation;
    private final ContentMapper contentMapper;

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

    public GetUserInfoRequestDto mapRequestToDto(GetUserInfoRequest request) {
        if (request.userId() == null && request.login() == null) {
            throw new ValidationException("Id or login must be not null");
        }

        if (request.userId() == null) {
            validationService.validateMaxInt(
                    userValidation.maxLengthLogin,
                    request.login().length(),
                    "Login is very long.",
                    "length"
            );
        } else {
            validationService.validateUserId(request.userId());
        }

        return GetUserInfoRequestDto.builder()
                .userId(request.userId())
                .login(request.login())
                .build();
    }

    public UserRegistrationResponse mapDtoToResponse(UserRegistrationResponseDto responseDto) {
        return UserRegistrationResponse.builder()
                .userId(responseDto.userId())
                .time(responseDto.time().toEpochMilli())
                .build();
    }

    public UserGetChatsResponse mapDtoToResponse(UserGetChatsResponseDto responseDto) {
        return UserGetChatsResponse.builder()
                .chats(responseDto.chats()
                        .stream()
                        .map(contentMapper::mapDtoToInfo)
                        .toList())
                .time(System.currentTimeMillis())
                .build();
    }

    public GetUserInfoResponse mapDtoToResponse(GetUserInfoResponseDto responseDto) {
        return GetUserInfoResponse.builder()
                .userInfo(contentMapper.mapDtoToInfo(responseDto.userInfo()))
                .time(System.currentTimeMillis())
                .build();
    }

}
