package com.earuile.bubble.core.repository.info;

import com.earuile.bubble.core.db.info.UserInfoDBService;
import com.earuile.bubble.public_interface.user.UserDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoRepository {
    private final UserInfoDBService userInfoDBService;
    private UserDataDto userInfo;

    public boolean load() {
        if (userInfo != null) {
            return true;
        }
        userInfo = userInfoDBService.load();
        return userInfo != null;
    }

    public void save() {
        if (userInfo != null) {
            userInfoDBService.save(userInfo);
        }
    }

    public void updateInfo(UserDataDto userInfo) {
        this.userInfo = userInfo;
    }

    public UserDataDto info() {
        return userInfo;
    }
}
