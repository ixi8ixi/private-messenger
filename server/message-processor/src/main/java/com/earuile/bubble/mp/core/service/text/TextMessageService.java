package com.earuile.bubble.mp.core.service.text;

import com.earuile.bubble.mp.db.content.text.TextDBService;
import com.earuile.bubble.mp.db.message.MessageDBService;
import com.earuile.bubble.mp.db.message.entity.ContentType;
import com.earuile.bubble.mp.db.message.entity.MessageEntity;
import com.earuile.bubble.mp.public_interface.Message;
import com.earuile.bubble.mp.public_interface.text.content.Text;
import com.earuile.bubble.mp.public_interface.text.dto.TextGetRequestDto;
import com.earuile.bubble.mp.public_interface.text.dto.TextGetResponseDto;
import com.earuile.bubble.mp.public_interface.text.dto.TextSendRequestDto;
import com.earuile.bubble.mp.public_interface.text.dto.TextSendResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TextMessageService {

    private final MessageDBService messageDBService;
    private final TextDBService textDBService;

    public TextGetResponseDto get(TextGetRequestDto requestDto) {
        return TextGetResponseDto.builder()
                .messages(messageDBService.getTextMessages(requestDto)
                        .stream()
                        .filter(message -> message.getContentType() == ContentType.TEXT) //???
                        .map(messageEntity -> Message.<Text>builder()
                                .userId(messageEntity.getUser().getId())
                                .id(messageEntity.getId())
                                .time(messageEntity.getTime())
                                .content(textDBService
                                        .getTextById(messageEntity.getContent())
                                        .map(text -> Text.builder()
                                                .text(text.getText())
                                                .build())
                                        .orElseThrow())
                                .build())
                        .toList())
                .build();
    }

    public TextSendResponseDto save(TextSendRequestDto requestDto) {
        MessageEntity messageEntity = messageDBService.createTextMessage(
                requestDto.text().text(),
                requestDto.userId(),
                requestDto.chatId());
        return TextSendResponseDto.builder()
                .message(Message.<Text>builder()
                        .userId(messageEntity.getUser().getId())
                        .id(messageEntity.getId())
                        .time(messageEntity.getTime())
                        .build())
                .build();
    }

}
