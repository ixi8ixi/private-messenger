package com.earuile.bubble.core.rest.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "server.chats")
public record ChatsRestInteractionProperty(
        String createChat,
        String appendUsers, // todo Я считаю, что нужно поменять метод на POST - доводы те же,
        //                      что и в случае с timestamp. Никто не будет сюда смотреть, но будет СИЛЬНО проще, если
        //                      все методы будут одинаковые - просто написанной документации в нашем случае будет выше крыши
        String getInfo
) {}
