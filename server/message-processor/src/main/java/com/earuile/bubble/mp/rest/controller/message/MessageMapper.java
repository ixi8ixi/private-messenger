package com.earuile.bubble.mp.rest.controller.message;

import com.earuile.bubble.mp.public_interface.message.content.MessageDto;
import com.earuile.bubble.mp.public_interface.message.text.content.TextDto;
import com.earuile.bubble.mp.public_interface.message.dto.MessageGetRequestDto;
import com.earuile.bubble.mp.public_interface.message.dto.MessageGetResponseDto;
import com.earuile.bubble.mp.public_interface.message.text.dto.TextSendRequestDto;
import com.earuile.bubble.mp.public_interface.message.text.dto.TextSendResponseDto;
import com.earuile.bubble.mp.rest.validation.ValidationService;
import com.earuile.bubble.mp.rest.controller.message.info.end_point.get.MessageGetRequest;
import com.earuile.bubble.mp.rest.controller.message.info.end_point.get.MessageGetResponse;
import com.earuile.bubble.mp.rest.controller.message.text.info.end_point.send.TextMessageSendRequest;
import com.earuile.bubble.mp.rest.controller.message.text.info.end_point.send.TextMessageSendResponse;
import com.earuile.bubble.mp.rest.controller.message.text.info.content.TextMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class MessageMapper {
    private final ValidationService validationService;

    public TextSendRequestDto mapRequestToDto(TextMessageSendRequest request) {
        validationService.validateChatId(request.chatId());

        return TextSendRequestDto.builder()
                .userId(request.textMessage().userId())
                .chatId(request.chatId())
                .textDto(decodeTextMessageToText(request.textMessage()))
                .build();
    }

    public MessageGetRequestDto mapRequestToDto(MessageGetRequest request) {
        validationService.validateChatId(request.chatId());
        if (request.userId() != null) {
            validationService.validateUserId(request.userId());
        }

        return MessageGetRequestDto.builder()
                .userId(request.userId())
                .chatId(request.chatId())
                .limit(request.limit())
                .lastKnownId(request.lastKnownId())
                .build();
    }

    public TextMessageSendResponse mapDtoToResponse(TextSendResponseDto responseDto) {
        return TextMessageSendResponse.builder()
                .id(responseDto.messageDto().id())
                .time(responseDto.messageDto().time().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

    // all message - to text message
    public MessageGetResponse mapDtoToResponse(MessageGetResponseDto responseDto) {
        return MessageGetResponse.builder()
                .chatId(responseDto.chatId())
                .textMessages(responseDto.messages()
                        .stream()
                        .map(this::encodeMessageDtoToTextMessage)
                        .toList())
                .time(System.currentTimeMillis())
                .build();
    }

    // must have new level for coding abstraction in future

    private TextDto decodeTextMessageToText(TextMessage textMessage) {
        return TextDto.builder()
                .text(textMessage.textData())
                .build();
    }

    @SuppressWarnings("unchecked")
    private TextMessage encodeMessageDtoToTextMessage(MessageDto<?> messageDto) {
        MessageDto<TextDto> messageTextDto = (MessageDto<TextDto>) messageDto;
        return TextMessage.builder()
                .userId(messageDto.userId())
                .textData(messageTextDto.content().text() + ", hello!")
                .id(messageDto.id())
                .timeDate(messageDto.time().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

}
