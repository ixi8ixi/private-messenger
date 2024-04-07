package com.earuile.bubble.mp.rest.message;

import com.earuile.bubble.mp.public_interface.message.MessageDto;
import com.earuile.bubble.mp.public_interface.message.text.content.Text;
import com.earuile.bubble.mp.public_interface.message.text.dto.MessageGetRequestDto;
import com.earuile.bubble.mp.public_interface.message.text.dto.MessageGetResponseDto;
import com.earuile.bubble.mp.public_interface.message.text.dto.TextSendRequestDto;
import com.earuile.bubble.mp.public_interface.message.text.dto.TextSendResponseDto;
import com.earuile.bubble.mp.rest.message.text.info.end_point.send.TextMessageSendResponse;
import com.earuile.bubble.mp.rest.message.text.info.content.TextMessage;
import com.earuile.bubble.mp.rest.message.info.get.MessageGetRequest;
import com.earuile.bubble.mp.rest.message.info.get.MessageGetResponse;
import com.earuile.bubble.mp.rest.message.text.info.end_point.send.TextMessageSendRequest;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class MessageMapper {

    public TextMessageSendResponse mapDtoToResponse(TextSendResponseDto responseDto) {
        return TextMessageSendResponse.builder()
                .id(responseDto.messageDto().id())
                .time(responseDto.messageDto().time().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

    public TextSendRequestDto mapRequestToDto(TextMessageSendRequest request) {
        return TextSendRequestDto.builder()
                .userId(request.textMessage().userId())
                .chatId(request.chatId())
                .text(decodeTextMessageToText(request.textMessage()))
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

    public MessageGetRequestDto mapRequestToDto(MessageGetRequest request) {
        return MessageGetRequestDto.builder()
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

    @SuppressWarnings("unchecked")
    private TextMessage encodeMessageDtoToTextMessage(MessageDto<?> messageDto) {
        MessageDto<Text> messageTextDto = (MessageDto<Text>) messageDto;
        return TextMessage.builder()
                .userId(messageDto.userId())
                .textData(messageTextDto.content().text() + ", hello!")
                .id(messageDto.id())
                .timeDate(messageDto.time().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

}
