package com.earuile.bubble.core.rest.dto;

import java.util.List;

public record ChatInfo(
       String id,
       String name,
       List<UserInfo> users, // todo Это не нужно передавать, ведь юзеров может быть много
       //                         (думаю что стоит убрать - ведь дальеш будет от этого избавляться ещё сложнее)
       Long time
) {}
