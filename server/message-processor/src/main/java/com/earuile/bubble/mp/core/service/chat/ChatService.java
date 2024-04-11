package com.earuile.bubble.mp.core.service.chat;

import com.earuile.bubble.mp.db.chat.ChatDBService;
import com.earuile.bubble.mp.db.chat.entity.ChatEntity;
import com.earuile.bubble.mp.db.content.ContentMapper;
import com.earuile.bubble.mp.db.exception.ChatNotFound;
import com.earuile.bubble.mp.db.exception.UserNotFound;
import com.earuile.bubble.mp.db.user.UserDBService;
import com.earuile.bubble.mp.db.user.entity.UserEntity;
import com.earuile.bubble.mp.public_interface.chat.add_user.dto.AddUsersToChatRequestDto;
import com.earuile.bubble.mp.public_interface.chat.add_user.dto.AddUsersToChatResponseDto;
import com.earuile.bubble.mp.public_interface.chat.chat_info.dto.GetChatInfoRequestDto;
import com.earuile.bubble.mp.public_interface.chat.chat_info.dto.GetChatInfoResponseDto;
import com.earuile.bubble.mp.public_interface.chat.create.dto.CreateChatRequestDto;
import com.earuile.bubble.mp.public_interface.chat.create.dto.CreateChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatDBService chatDBService;
    private final UserDBService userDBService;
    private final ContentMapper contentMapper;

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

    @Transactional
    public GetChatInfoResponseDto get(GetChatInfoRequestDto requestDto) {
        ChatEntity chat = getChatElseThrow(requestDto.chatId());

        return GetChatInfoResponseDto.builder()
                .chatInfoDto(contentMapper.mapEntityToDto(chat, false))
                .build();
    }

    @Transactional
    public AddUsersToChatResponseDto addUsers(AddUsersToChatRequestDto requestDto) {
        ChatEntity chat = getChatElseThrow(requestDto.chatId());

        Set<UserEntity> chatUsers = chat.getUsers();

        requestDto.userIds()
                .stream()
                .map(this::getUserElseThrow)
                .forEach(chatUsers::add);

        return AddUsersToChatResponseDto.builder()
                .chatInfo(contentMapper.mapEntityToDto(chat, false))
                .build();
    }

    private UserEntity getUserElseThrow(String id) {
        return userDBService.getById(id)
                .orElseThrow(() -> new UserNotFound("User with id %s wasn't found.".formatted(id)));
    }

    private ChatEntity getChatElseThrow(String id) {
        return chatDBService
                .getById(id)
                .orElseThrow(() -> new ChatNotFound("Chat with id %s wasn't found.".formatted(id)));
    }

}
