package com.earuile.bubble.core.repository.chat;

import com.earuile.bubble.public_interface.chat.ChatInfoDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class ChatRepository {
    private final ConcurrentMap<String, ChatInfoDto> idToChatInfo = new ConcurrentHashMap<>();

    public void saveChats(List<ChatInfoDto> chats) {
        chats.forEach(chatInfo -> idToChatInfo.put(chatInfo.id(), chatInfo));
    }

    public ChatInfoDto chat(String chatId) {
        return idToChatInfo.get(chatId);
    }

    public Collection<ChatInfoDto> allChats() {
        return idToChatInfo.values();
    }
}
