package com.earuile.bubble.mp.db.chat;

import com.earuile.bubble.mp.db.chat.entity.ChatEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatDBService {
    private final ChatRepository repository;

    public ChatEntity createAndGet(ChatEntity chat) {
           return repository.save(chat);
    }

    public Optional<ChatEntity> getById(String id) {
        return repository.findById(id);
    }

}
