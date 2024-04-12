package com.earuile.bubble.core.db.info;

import com.earuile.bubble.core.db.info.entity.UserInfoEntity;
import com.earuile.bubble.public_interface.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private UserInfoEntity userInfo;

    public boolean loadInfo() {
        if (isPresent()) {
            return true;
        }

        List<UserInfoEntity> infoList = userInfoRepository.findAll();
        if (!infoList.isEmpty()) {
            userInfo = infoList.getFirst();
            return true;
        }
        return false;
    }

    public void updateInfo(UserInfoDto infoDto) {
        UserInfoEntity entity = UserInfoEntity.builder()
                .id(infoDto.id())
                .login(infoDto.login())
                .name(infoDto.name())
                .password(infoDto.password())
                .build();
        userInfoRepository.save(entity);
        userInfo = entity;
    }

    public boolean isPresent() {
        return userInfo == null;
    }

    public String id() {
        return userInfo.getId();
    }

    public String name() {
        return userInfo.getName();
    }

    public String login() {
        return userInfo.getLogin();
    }
}
