package com.earuile.bubble.core.repository;

import com.earuile.bubble.core.repository.info.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataLoader {
    private final UserInfoRepository userInfoRepository;

    public boolean load() {
        return userInfoRepository.load();
    }

    public void save() {
        userInfoRepository.save();
    }
}
