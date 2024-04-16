package com.earuile.bubble.core.controller;

import com.earuile.bubble.core.repository.message.MessageRepository;
import com.earuile.bubble.core.rest.interaction.MessagesRestService;
import com.earuile.bubble.public_interface.message.MessageModelDto;
import com.earuile.bubble.public_interface.chat.SendMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class ChatMessageDataController {
    private static final int DEFAULT_BATCH_SIZE = 100;
    private final MessageRepository messageRepository;
    private final MessagesRestService messagesRestService;
    private final ConcurrentMap<String, AtomicBoolean> chatIdToProgress = new ConcurrentHashMap<>();

    public List<MessageModelDto> loadCached(String chatId) {
        return messageRepository.loadCached(chatId);
    }

    /**
     * Pull batch for specified chat after last known id.
     */
    public List<MessageModelDto> pullMessagesFromServer(String chatId, String lastKnownId) {
        chatIdToProgress.putIfAbsent(chatId, new AtomicBoolean(false));
        if (chatIdToProgress.get(chatId).compareAndSet(false, true)) {
            try {
                List<MessageModelDto> batch = messagesRestService.pullMessages(lastKnownId, chatId, DEFAULT_BATCH_SIZE);
                if (!batch.isEmpty()) {
                    return messageRepository.updateAndAppendToList(chatId, batch);
                }
                return null;
            } finally {
                chatIdToProgress.get(chatId).set(false);
            }
        }
        return null;
    }

    public void sendMessage(SendMessageDto dto) {
        messagesRestService.saveMessage(dto);
    }
}
