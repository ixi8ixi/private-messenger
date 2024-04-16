package com.earuile.bubble.core.db.info;

import com.earuile.bubble.core.db.info.entity.UserInfoEntity;
import com.earuile.bubble.public_interface.user.UserDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoDBService {
    private final UserInfoDBRepository userInfoDBRepository;

    public UserDataDto load() {
        List<UserInfoEntity> infoList = userInfoDBRepository.findAll();
        if (!infoList.isEmpty()) {
            UserInfoEntity entity = infoList.getFirst();
            return new UserDataDto(
                    entity.getId(),
                    entity.getLogin(),
                    entity.getName(),
                    entity.getPassword()
            );
        }
        return null;
    }

    public void save(UserDataDto infoDto) {
        UserInfoEntity entity = UserInfoEntity.builder()
                .id(infoDto.id())
                .login(infoDto.login())
                .name(infoDto.name())
                .password(infoDto.password())
                .build();
        userInfoDBRepository.save(entity);
    }
}
