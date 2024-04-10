package com.earuile.bubble.mp.db.message;

import com.earuile.bubble.mp.db.chat.ChatDBService;
import com.earuile.bubble.mp.db.chat.entity.ChatEntity;
import com.earuile.bubble.mp.db.message.text.TextDBService;
import com.earuile.bubble.mp.db.exception.ChatNotFound;
import com.earuile.bubble.mp.db.exception.UserNotFound;
import com.earuile.bubble.mp.db.message.entity.ContentType;
import com.earuile.bubble.mp.db.message.entity.MessageEntity;
import com.earuile.bubble.mp.db.user.UserDBService;
import com.earuile.bubble.mp.public_interface.message.dto.MessageGetRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                        .user(userDBService
                                .getById(userId)
                                .orElseThrow(() ->
                                        new UserNotFound("User with transmitted userId wasn't found.")))
                        .chat(chatDBService
                                .getById(chatId)
                                .orElseThrow(() ->
                                        new ChatNotFound("Chat with transmitted chatId wasn't found.")))
                        .contentType(ContentType.TEXT)
                        .content(textDBService.createAndGet(text).getId())
                        .build()
        );
    }

    /**
     * @return {@code limit} messages after {@code lastKnownId} only for user with {@code userId}.
     */
    public List<MessageEntity> getTextMessages(MessageGetRequestDto dto) {
        // Stupid implementation
        return chatDBService.getById(dto.chatId())
                .map(ChatEntity::getMessages)
                .map(messages -> messages.stream()
                        .sorted(Comparator.comparing(MessageEntity::getTime))
                        .dropWhile(message -> dto.lastKnownId() != null && !message.getId().equals(dto.lastKnownId()))
                        .skip(dto.lastKnownId() == null ? 0 : 1)
                        .limit(dto.limit())
                        .filter(message -> dto.userId() == null || message.getUser().getId().equals(dto.userId()))
                        .toList())
                .orElseThrow(() -> new ChatNotFound("Chat with transmitted chatId wasn't found."));
    }

    /**
     * Save message to db and return entity.
     *
     * @param message with userId, chatId and has already created id of content.
     */
    private MessageEntity createMessage(MessageEntity message) {
        return messageRepository.save(message);
    }

}
