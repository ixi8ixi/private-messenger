package com.earuile.bubble.mp.core.service.message.text;

import com.earuile.bubble.mp.db.message.MessageDBService;
import com.earuile.bubble.mp.db.message.entity.MessageEntity;
import com.earuile.bubble.mp.public_interface.message.MessageDto;
import com.earuile.bubble.mp.public_interface.message.text.content.Text;
import com.earuile.bubble.mp.public_interface.message.text.dto.TextSendRequestDto;
import com.earuile.bubble.mp.public_interface.message.text.dto.TextSendResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TextMessageService {
    private final MessageDBService messageDBService;

    public TextSendResponseDto save(TextSendRequestDto requestDto) {
        MessageEntity messageEntity = messageDBService.createTextMessage(
                requestDto.text().text(),
                requestDto.userId(),
                requestDto.chatId());
        return TextSendResponseDto.builder()
                .messageDto(MessageDto.<Text>builder()
                        .userId(messageEntity.getUser().getId())
                        .id(messageEntity.getId())
                        .time(messageEntity.getTime())
                        .build())
                .build();
    }

}
