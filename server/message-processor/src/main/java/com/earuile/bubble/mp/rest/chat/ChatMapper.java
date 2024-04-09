package com.earuile.bubble.mp.rest.chat;

import com.earuile.bubble.mp.public_interface.chat.create.dto.CreateChatRequestDto;
import com.earuile.bubble.mp.public_interface.chat.create.dto.CreateChatResponseDto;
import com.earuile.bubble.mp.rest.ValidationService;
import com.earuile.bubble.mp.rest.chat.info.end_point.create.CreateChatRequest;
import com.earuile.bubble.mp.rest.chat.info.end_point.create.CreateChatResponse;
import com.earuile.bubble.mp.rest.chat.validation.ChatValidation;
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

    public CreateChatResponse mapDtoToResponse(CreateChatResponseDto responseDto) {
        return CreateChatResponse.builder()
                .id(responseDto.id())
                .time(responseDto.time().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

}
