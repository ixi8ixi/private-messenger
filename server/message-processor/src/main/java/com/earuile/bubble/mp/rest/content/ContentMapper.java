package com.earuile.bubble.mp.rest.content;

import com.earuile.bubble.mp.public_interface.content.dto.ChatInfoDto;
import com.earuile.bubble.mp.public_interface.content.dto.UserInfoDto;
import com.earuile.bubble.mp.rest.content.info.ChatInfo;
import com.earuile.bubble.mp.rest.content.info.UserInfo;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class ContentMapper {

    public UserInfo mapDtoToInfo(UserInfoDto userInfoDto) {
        return UserInfo.builder()
                .id(userInfoDto.id())
                .login(userInfoDto.login())
                .name(userInfoDto.name())
                .build();
    }

    public ChatInfo mapDtoToInfo(ChatInfoDto chatInfoDto) {
        return ChatInfo.builder()
                .id(chatInfoDto.id())
                .name(chatInfoDto.name())
                .users(chatInfoDto.users() == null ? null : chatInfoDto.users()
                        .stream()
                        .map(this::mapDtoToInfo)
                        .toList())
                .time(chatInfoDto.time().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

}
