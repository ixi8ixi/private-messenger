package com.earuile.bubble.mp.rest.controller.chat;

import com.earuile.bubble.mp.public_interface.chat.chat_info.GetChatInfoRequestDto;
import com.earuile.bubble.mp.public_interface.chat.chat_info.GetChatInfoResponseDto;
import com.earuile.bubble.mp.public_interface.chat.create.dto.CreateChatRequestDto;
import com.earuile.bubble.mp.public_interface.chat.create.dto.CreateChatResponseDto;
import com.earuile.bubble.mp.rest.content.UserInfo;
import com.earuile.bubble.mp.rest.controller.ValidationService;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.chat_info.GetChatInfoRequest;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.chat_info.GetChatInfoResponse;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.create.CreateChatRequest;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.create.CreateChatResponse;
import com.earuile.bubble.mp.rest.controller.chat.validation.ChatValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class ChatMapper {
    private final ValidationService validationService;
    private final ChatValidation chatValidation;


    public CreateChatRequestDto mapRequestToDto(CreateChatRequest request) {
        validationService.validateUserId(request.creatorUserId());
        validationService.validateMaxInt(
                chatValidation.maxLengthName,
                request.name().length(),
                "Name is very long.",
                "length"
        );
        request.userIds().forEach(validationService::validateUserId);

        return CreateChatRequestDto.builder()
                .creatorUserId(request.creatorUserId())
                .name(request.name())
                .userIds(request.userIds())
                .build();
    }

    public GetChatInfoRequestDto mapRequestToDto(GetChatInfoRequest request) {
        validationService.validateChatId(request.chatId());

        return GetChatInfoRequestDto.builder()
                .chatId(request.chatId())
                .build();
    }

    public CreateChatResponse mapDtoToResponse(CreateChatResponseDto responseDto) {
        return CreateChatResponse.builder()
                .id(responseDto.id())
                .time(responseDto.time().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

    public GetChatInfoResponse mapDtoToResponse(GetChatInfoResponseDto responseDto) {
        return GetChatInfoResponse.builder()
                .chatId(responseDto.chatId())
                .name(responseDto.name())
                .users(responseDto.users()
                        .stream()
                        .map(userInfoDto -> UserInfo.builder()
                                .id(userInfoDto.id())
                                .login(userInfoDto.login())
                                .name(userInfoDto.name())
                                .build())
                        .toList())
                .time(responseDto.time().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

}
