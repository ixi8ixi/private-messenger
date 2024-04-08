package com.earuile.bubble.rest.dto.rest;

public record TextMessage(
        String userId,
        String textData,
        String id,
        Long timeDate
) {}
