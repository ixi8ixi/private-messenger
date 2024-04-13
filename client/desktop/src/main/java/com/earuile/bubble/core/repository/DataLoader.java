package com.earuile.bubble.core.repository;

import com.earuile.bubble.core.repository.chat.ChatRepository;
import com.earuile.bubble.core.repository.info.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataLoader {
    private final UserInfoRepository userInfoRepository;
    private final ChatRepository chatRepository;

    public boolean load() {
        if (!userInfoRepository.load()) {
            return false;
        }

        chatRepository.load();
        return true;
    }

    public void save() {
        userInfoRepository.save();
        chatRepository.load();
    }
}
