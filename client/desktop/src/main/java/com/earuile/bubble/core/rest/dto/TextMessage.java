package com.earuile.bubble.core.rest.dto;

public record TextMessage(
        String userId,
        String textData,
        String id,
        Long timeDate
) {}
