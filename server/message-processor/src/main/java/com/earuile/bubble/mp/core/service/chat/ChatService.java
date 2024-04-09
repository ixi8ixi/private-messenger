package com.earuile.bubble.mp.core.service.chat;

import com.earuile.bubble.mp.db.chat.ChatDBService;
import com.earuile.bubble.mp.db.chat.entity.ChatEntity;
import com.earuile.bubble.mp.db.exception.UserNotFound;
import com.earuile.bubble.mp.db.user.UserDBService;
import com.earuile.bubble.mp.db.user.entity.UserEntity;
import com.earuile.bubble.mp.public_interface.chat.create.dto.CreateChatRequestDto;
import com.earuile.bubble.mp.public_interface.chat.create.dto.CreateChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatDBService chatDBService;
    private final UserDBService userDBService;

    @Transactional
    public CreateChatResponseDto create(CreateChatRequestDto requestDto) {
        requestDto.userIds().add(requestDto.creatorUserId());
        ChatEntity chat = chatDBService.createAndGet(ChatEntity.builder()
                .name(requestDto.name())
                .users(requestDto.userIds()
                        .stream()
                        .map(this::getUserElseThrow)
                        .collect(Collectors.toSet()))
                .build());

        return CreateChatResponseDto.builder()
                .id(chat.getId())
                .time(chat.getTime())
                .build();
    }

    private UserEntity getUserElseThrow(String id) {
        return userDBService.getById(id)
                .orElseThrow(() -> new UserNotFound("User with id %s wasn't found.".formatted(id)));
    }

}
