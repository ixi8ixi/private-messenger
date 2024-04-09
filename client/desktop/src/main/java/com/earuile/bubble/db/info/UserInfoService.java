package com.earuile.bubble.db.info;

import com.earuile.bubble.db.info.entity.UserInfoEntity;
import com.earuile.bubble.public_interface.UserInfoDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private UserInfoEntity userInfo;

    @PostConstruct
    private void loadInfo() {
        List<UserInfoEntity> infoList = userInfoRepository.findAll();
        if (!infoList.isEmpty()) {
            userInfo = infoList.getFirst();
        }
    }

    public void updateInfo(UserInfoDto infoDto) {
        UserInfoEntity entity = UserInfoEntity.builder()
                .id(infoDto.id())
                .login(infoDto.login())
                .name(infoDto.name())
                .password(infoDto.password())
                .build();
        userInfoRepository.save(entity);
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
