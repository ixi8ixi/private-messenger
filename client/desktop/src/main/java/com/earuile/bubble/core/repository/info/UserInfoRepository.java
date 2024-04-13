package com.earuile.bubble.core.repository.info;

import com.earuile.bubble.core.db.info.UserInfoDBService;
import com.earuile.bubble.public_interface.UserDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoRepository {
    private final UserInfoDBService userInfoDBService;
    private UserDataDto userInfo;

    public void load() {
        userInfo = userInfoDBService.load();
    }

    public void save() {
        if (userInfo != null) {
            userInfoDBService.save(userInfo);
        }
    }
}
