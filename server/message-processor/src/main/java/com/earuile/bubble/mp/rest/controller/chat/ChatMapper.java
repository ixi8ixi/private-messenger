package com.earuile.bubble.mp.rest.controller.chat;

import com.earuile.bubble.mp.public_interface.chat.add_user.dto.AddUsersToChatRequestDto;
import com.earuile.bubble.mp.public_interface.chat.add_user.dto.AddUsersToChatResponseDto;
import com.earuile.bubble.mp.public_interface.chat.chat_info.dto.GetChatInfoRequestDto;
import com.earuile.bubble.mp.public_interface.chat.chat_info.dto.GetChatInfoResponseDto;
import com.earuile.bubble.mp.public_interface.chat.create.dto.CreateChatRequestDto;
import com.earuile.bubble.mp.public_interface.chat.create.dto.CreateChatResponseDto;
import com.earuile.bubble.mp.rest.content.ContentMapper;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.add_user.AddUsersToChatRequest;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.add_user.AddUsersToChatResponse;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.chat_info.GetChatInfoRequest;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.chat_info.GetChatInfoResponse;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.create.CreateChatRequest;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.create.CreateChatResponse;
import com.earuile.bubble.mp.rest.controller.chat.validation.ChatValidation;
import com.earuile.bubble.mp.rest.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMapper {
    private final ValidationService validationService;
    private final ChatValidation chatValidation;
    private final ContentMapper contentMapper;

    public CreateChatRequestDto mapRequestToDto(CreateChatRequest request) {
        validationService.validateUserId(request.creatorUserId());
        validationService.validateMaxInt(
                chatValidation.maxLengthName,
                request.name().length(),
                "Name is very long.",
                "length"
        );
        if (request.userIds() != null) {
            request.userIds().forEach(validationService::validateUserId);
        }

        return CreateChatRequestDto.builder()
                .creatorUserId(request.creatorUserId())
                .name(request.name())
                .userIds(request.userIds() != null ? request.userIds() : new ArrayList<>())
                .build();
    }

    public GetChatInfoRequestDto mapRequestToDto(GetChatInfoRequest request) {
        validationService.validateChatId(request.chatId());

        return GetChatInfoRequestDto.builder()
                .chatId(request.chatId())
                .build();
    }

    public AddUsersToChatRequestDto mapRequestToDto(AddUsersToChatRequest request) {
        validationService.validateChatId(request.chatId());
        request.userIds().forEach(validationService::validateUserId);

        return AddUsersToChatRequestDto.builder()
                .chatId(request.chatId())
                .userIds(request.userIds())
                .build();
    }

    public CreateChatResponse mapDtoToResponse(CreateChatResponseDto responseDto) {
        return CreateChatResponse.builder()
                .id(responseDto.id())
                .time(responseDto.time().toEpochMilli())
                .build();
    }

    public GetChatInfoResponse mapDtoToResponse(GetChatInfoResponseDto responseDto) {
        return GetChatInfoResponse.builder()
                .chatInfo(contentMapper.mapDtoToInfo(responseDto.chatInfoDto()))
                .time(System.currentTimeMillis())
                .build();
    }

    public AddUsersToChatResponse mapDtoToResponse(AddUsersToChatResponseDto responseDto) {
        return AddUsersToChatResponse.builder()
                .chatInfo(contentMapper.mapDtoToInfo(responseDto.chatInfo()))
                .time(System.currentTimeMillis())
                .build();
    }

}
