package com.earuile.bubble.mp.db.message;

import com.earuile.bubble.mp.db.chat.ChatDBService;
import com.earuile.bubble.mp.db.chat.entity.ChatEntity;
import com.earuile.bubble.mp.db.content.text.TextDBService;
import com.earuile.bubble.mp.db.message.entity.ContentType;
import com.earuile.bubble.mp.db.message.entity.MessageEntity;
import com.earuile.bubble.mp.db.user.UserDBService;
import com.earuile.bubble.mp.public_interface.text.dto.TextGetRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageDBService {
    private final MessageRepository messageRepository;
    private final UserDBService userDBService;
    private final ChatDBService chatDBService;
    private final TextDBService textDBService;

    public MessageEntity createTextMessage(String text, String userId, String chatId) {
        return createMessage(
                MessageEntity.builder()
                        .user(userDBService.getById(userId).orElseThrow())
                        .chat(chatDBService.getById(chatId).orElseThrow())
                        .contentType(ContentType.TEXT)
                        .content(textDBService.createAndGet(text).getId())
                        .build()
        );
    }

    public List<MessageEntity> getTextMessages(TextGetRequestDto dto) {
        return chatDBService.getById(dto.chatId())
                .map(ChatEntity::getMessages)
                .map(messages -> messages.stream()
                        .sorted(Comparator.comparing(MessageEntity::getTime))
                        .dropWhile(message -> !message.getId().equals(dto.lastKnownId()))
                        .limit(dto.limit())
                        .toList())
                .orElse(Collections.emptyList());
    }

    /**
     * Save message to db and return entity.
     * @param message with userId, chatId and has already created id of content.
     */
    private MessageEntity createMessage(MessageEntity message) {
        return messageRepository.save(message);
    }

}
