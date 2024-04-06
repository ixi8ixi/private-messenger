package com.earuile.bubble.mp.rest.text;

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

@Component
public class TextMessageMapper {

    public TextMessageSendResponse mapDtoToResponse(TextSendResponseDto responseDto) {
        return TextMessageSendResponse.builder()
                .time(responseDto.time())
                .build();
    }

    public TextSendRequestDto mapRequestToDto(TextMessageSendRequest request) {
        return TextSendRequestDto.builder()
                .userId(request.userId())
                .chatId(request.chatId())
                .text(decodeTextMessageToText(request.textMessage()))
                .build();
    }

    public TextMessageGetResponse mapDtoToResponse(TextGetResponseDto responseDto) {
        return TextMessageGetResponse.builder()
                .userId(responseDto.userId())
                .chatId(responseDto.chatId())
                .textMessages(responseDto.texts()
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

    private TextMessage encodeTextToTextMessage(Text text) {
        return TextMessage.builder()
                .textData(text.text() + ", hello!")
                .build();
    }

}
