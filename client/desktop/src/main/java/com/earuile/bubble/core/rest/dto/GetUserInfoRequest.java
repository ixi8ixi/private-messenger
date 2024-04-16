package com.earuile.bubble.core.rest.dto;

public record GetUserInfoRequest(
        String userId,
        String login // todo Убрать - ведь пока пользователь в оффлайне айди может поменяться и всё сломается
) {}
