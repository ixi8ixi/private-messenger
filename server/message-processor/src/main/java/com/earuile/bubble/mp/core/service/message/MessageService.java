package com.earuile.bubble.mp.core.service.message;

import com.earuile.bubble.mp.db.message.text.TextDBService;
import com.earuile.bubble.mp.db.message.MessageDBService;
import com.earuile.bubble.mp.db.message.entity.ContentType;
import com.earuile.bubble.mp.db.message.entity.MessageEntity;
import com.earuile.bubble.mp.public_interface.message.content.MessageDto;
import com.earuile.bubble.mp.public_interface.message.text.content.TextDto;
import com.earuile.bubble.mp.public_interface.message.dto.MessageGetRequestDto;
import com.earuile.bubble.mp.public_interface.message.dto.MessageGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageDBService messageDBService;
    private final TextDBService textDBService;

    /**
     * Get {@code limit} count messages after {@code lastKnownId} from chat with {@code chatId}.
     * From start if {@code lastKnownId == null}. Only from user with {@code userId} if {@code userId != null}.
     * In current implementation cast all type messages to text messages.
     *
     * @return responseDto.
     */
    @Transactional
    public MessageGetResponseDto get(MessageGetRequestDto requestDto) {
        return MessageGetResponseDto.builder()
                .messages(messageDBService.getTextMessages(requestDto)
                        .stream()
                        .map(this::messageEntityToMessageDto)
                        .toList())
                .build();
    }

    private MessageDto<Object> messageEntityToMessageDto(MessageEntity messageEntity) {
        return MessageDto.builder()
                .userId(messageEntity.getUser().getId())
                .id(messageEntity.getId())
                .time(messageEntity.getTime())
                .content(getContent(messageEntity))
                .build();
    }

    /**
     * Very simple implementation getting content.
     *
     * @return text value if {@code contentType is TEXT} else link on content in string.
     */
    private TextDto getContent(MessageEntity messageEntity) {
        if (messageEntity.getContentType() == ContentType.TEXT) {
            return textDBService
                    .getTextById(messageEntity.getContent())
                    .map(text -> TextDto.builder()
                            .text(text.getText())
                            .build())
                    .orElseThrow();
        } else {
            return TextDto.builder()
                    .text(Long.toString(messageEntity.getContent()))
                    .build();
        }
    }

}
