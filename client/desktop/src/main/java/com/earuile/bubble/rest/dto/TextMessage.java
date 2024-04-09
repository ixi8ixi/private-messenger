package com.earuile.bubble.rest.dto;

public record TextMessage(
        String userId,
        String textData,
        String id,
        Long timeDate
) {}
