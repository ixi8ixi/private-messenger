package com.earuile.bubble.mp.core.service.user;

import com.earuile.bubble.mp.core.service.user.exception.CannotRegisterUser;
import com.earuile.bubble.mp.db.chat.entity.ChatEntity;
import com.earuile.bubble.mp.db.content.ContentEntityMapper;
import com.earuile.bubble.mp.db.exception.UserNotFound;
import com.earuile.bubble.mp.db.user.UserDBService;
import com.earuile.bubble.mp.db.user.entity.UserEntity;
import com.earuile.bubble.mp.public_interface.user.chat.dto.UserGetChatsRequestDto;
import com.earuile.bubble.mp.public_interface.user.chat.dto.UserGetChatsResponseDto;
import com.earuile.bubble.mp.public_interface.user.registration.dto.UserRegistrationRequestDto;
import com.earuile.bubble.mp.public_interface.user.registration.dto.UserRegistrationResponseDto;
import com.earuile.bubble.mp.public_interface.user.user_info.GetUserInfoRequestDto;
import com.earuile.bubble.mp.public_interface.user.user_info.GetUserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDBService userDBService;
    private final ContentEntityMapper contentMapper;

    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto) {
        try {
            UserEntity user = userDBService.createAndGet(UserEntity.builder()
                    .login(requestDto.login())
                    .password(requestDto.password())
                    .name(requestDto.name())
                    .build());

            return UserRegistrationResponseDto.builder()
                    .userId(user.getId())
                    .time(user.getTime())
                    .build();
        } catch (DataAccessException e) {
            throw new CannotRegisterUser(e);
        }
    }

    public UserGetChatsResponseDto getChats(UserGetChatsRequestDto requestDto) {
        List<ChatEntity> chats = userDBService
                .getById(requestDto.userId())
                .map(user -> user.getChats().stream().toList())
                .orElseThrow(() -> new UserNotFound("User with transmitted userId wasn't found."));

        return UserGetChatsResponseDto.builder()
                .chats(chats.stream()
                        .map(chat -> contentMapper.mapEntityToDto(chat, true))
                        .toList())
                .build();
    }

    public GetUserInfoResponseDto get(GetUserInfoRequestDto requestDto) {
        if (requestDto.userId() == null && requestDto.login() == null) {
            throw new IllegalArgumentException("Id or login must be not null");
        }

        UserEntity user;
        if (requestDto.userId() == null) {
            user = userDBService.getByLogin(requestDto.login())
                    .orElseThrow(() -> new UserNotFound("User with transmitted login wasn't found."));
        } else {
            user = userDBService.getById(requestDto.userId())
                    .orElseThrow(() -> new UserNotFound("User with transmitted userId wasn't found."));
        }

        return GetUserInfoResponseDto.builder()
                .userInfo(contentMapper.mapEntityToDto(user))
                .build();
    }

}
