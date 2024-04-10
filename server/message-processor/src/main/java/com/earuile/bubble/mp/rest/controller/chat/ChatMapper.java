package com.earuile.bubble.mp.rest.controller.chat;

import com.earuile.bubble.mp.public_interface.chat.chat_info.dto.GetChatInfoRequestDto;
import com.earuile.bubble.mp.public_interface.chat.chat_info.dto.GetChatInfoResponseDto;
import com.earuile.bubble.mp.public_interface.chat.create.dto.CreateChatRequestDto;
import com.earuile.bubble.mp.public_interface.chat.create.dto.CreateChatResponseDto;
import com.earuile.bubble.mp.rest.content.ContentMapper;
import com.earuile.bubble.mp.rest.controller.ValidationService;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.chat_info.GetChatInfoRequest;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.chat_info.GetChatInfoResponse;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.create.CreateChatRequest;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.create.CreateChatResponse;
import com.earuile.bubble.mp.rest.controller.chat.validation.ChatValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.ArrayList;

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

    public CreateChatResponse mapDtoToResponse(CreateChatResponseDto responseDto) {
        return CreateChatResponse.builder()
                .id(responseDto.id())
                .time(responseDto.time().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

    public GetChatInfoResponse mapDtoToResponse(GetChatInfoResponseDto responseDto) {
        return GetChatInfoResponse.builder()
                .chatInfo(contentMapper.mapDtoToInfo(responseDto.chatInfoDto()))
                .build();
    }

}
