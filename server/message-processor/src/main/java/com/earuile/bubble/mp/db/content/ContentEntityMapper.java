package com.earuile.bubble.mp.db.content;

import com.earuile.bubble.mp.db.chat.entity.ChatEntity;
import com.earuile.bubble.mp.db.user.entity.UserEntity;
import com.earuile.bubble.mp.public_interface.content.dto.ChatInfoDto;
import com.earuile.bubble.mp.public_interface.content.dto.UserInfoDto;
import org.springframework.stereotype.Component;

@Component
public class ContentEntityMapper {

    public UserInfoDto mapEntityToDto(UserEntity userEntity) {
        return UserInfoDto.builder()
                .id(userEntity.getId())
                .login(userEntity.getLogin())
                .name(userEntity.getName())
                .time(userEntity.getTime())
                .build();
    }

    public ChatInfoDto mapEntityToDto(ChatEntity chatEntity, boolean withoutUsersInfo) {
        return ChatInfoDto.builder()
                .id(chatEntity.getId())
                .name(chatEntity.getName())
                .users(withoutUsersInfo ? null : chatEntity.getUsers()
                        .stream()
                        .map(this::mapEntityToDto)
                        .toList())
                .time(chatEntity.getTime())
                .build();
    }

}
