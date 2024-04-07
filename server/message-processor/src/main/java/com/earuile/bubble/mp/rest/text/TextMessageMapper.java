package com.earuile.bubble.mp.rest.text;

import com.earuile.bubble.mp.public_interface.Message;
import com.earuile.bubble.mp.public_interface.text.content.Text;
import com.earuile.bubble.mp.public_interface.text.dto.TextGetRequestDto;
import com.earuile.bubble.mp.public_interface.text.dto.TextGetResponseDto;
import com.earuile.bubble.mp.public_interface.text.dto.TextSendRequestDto;
import com.earuile.bubble.mp.public_interface.text.dto.TextSendResponseDto;
import com.earuile.bubble.mp.rest.text.info.content.TextMessage;
import com.earuile.bubble.mp.rest.text.info.end_point.get.TextMessageGetRequest;
import com.earuile.bubble.mp.rest.text.info.end_point.get.TextMessageGetResponse;
import com.earuile.bubble.mp.rest.text.info.end_point.send.TextMessageSendRequest;
import com.earuile.bubble.mp.rest.text.info.end_point.send.TextMessageSendResponse;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class TextMessageMapper {

    public TextMessageSendResponse mapDtoToResponse(TextSendResponseDto responseDto) {
        return TextMessageSendResponse.builder()
                .id(responseDto.message().id())
                .time(responseDto.message().time().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

    public TextSendRequestDto mapRequestToDto(TextMessageSendRequest request) {
        return TextSendRequestDto.builder()
                .userId(request.textMessage().userId())
                .chatId(request.chatId())
                .text(decodeTextMessageToText(request.textMessage()))
                .build();
    }

    public TextMessageGetResponse mapDtoToResponse(TextGetResponseDto responseDto) {
        return TextMessageGetResponse.builder()
                .chatId(responseDto.chatId())
                .textMessages(responseDto.messages()
                        .stream()
                        .map(this::encodeTextToTextMessage)
                        .toList())
                .build();
    }

    public TextGetRequestDto mapRequestToDto(TextMessageGetRequest request) {
        return TextGetRequestDto.builder()
                .userId(request.userId())
                .chatId(request.chatId())
                .limit(request.limit())
                .lastKnownId(request.lastKnownId())
                .build();
    }


    // must have new level for coding abstraction in future

    private Text decodeTextMessageToText(TextMessage textMessage) {
        return Text.builder()
                .text(textMessage.textData())
                .build();
    }

    private TextMessage encodeTextToTextMessage(Message<Text> message) {
        return TextMessage.builder()
                .userId(message.userId())
                .textData(message.content().text() + ", hello!")
                .id(message.id())
                .build();
    }

}
