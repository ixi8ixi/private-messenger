package com.earuile.bubble.core.repository.message;

import com.earuile.bubble.public_interface.message.MessageModelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

// todo Сделать ограничение на размер
@Service
@RequiredArgsConstructor
public class MessageRepository {
    private final ReentrantLock mapLock = new ReentrantLock();
    private ConcurrentMap<String, List<MessageModelDto>> chatIdToMessages = new ConcurrentHashMap<>();
    // todo правда ли мне нужна атомарность

    public void load() {
        // todo to be implemented
    }

    public void save() {
        // todo to be implemented
    }


    public List<MessageModelDto> updateAndAppendToList(String chatId, List<MessageModelDto> messages) {
        mapLock.lock();
        if (!chatIdToMessages.containsKey(chatId)) {
            chatIdToMessages.put(chatId, new ArrayList<>());
        }
        chatIdToMessages.get(chatId).addAll(messages);
        mapLock.unlock();
        return new ArrayList<>(messages);
    }

    public List<MessageModelDto> loadCached(String chatId) {
        List<MessageModelDto> list = chatIdToMessages.get(chatId);
        if (list == null) {
            return List.of();
        }
        return new ArrayList<>(list);
    }
}
