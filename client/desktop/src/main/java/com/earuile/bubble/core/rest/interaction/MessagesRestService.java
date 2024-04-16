package com.earuile.bubble.core.rest.interaction;

import com.earuile.bubble.core.rest.config.property.HostRestProperty;
import com.earuile.bubble.core.rest.config.property.MessagesRestInteractionProperty;
import com.earuile.bubble.core.rest.dto.*;
import com.earuile.bubble.core.util.LocalizedMessageException;
import com.earuile.bubble.public_interface.message.MessageModelDto;
import com.earuile.bubble.public_interface.chat.SendMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessagesRestService {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final HostRestProperty hostRestProperty;
    private final MessagesRestInteractionProperty messagesRestInteractionProperty;
    private final RestTemplate restTemplate;

    public List<MessageModelDto> pullMessages(String userId, String lastKnownId, String chatId, int limit) {
        try {
            MessageGetRequest request = new MessageGetRequest(userId, chatId, limit, lastKnownId);
            MessageGetResponse response = restTemplate.postForEntity(
                    hostRestProperty.host() + messagesRestInteractionProperty.pullMessages(),
                    request, MessageGetResponse.class).getBody();

            if (response == null) {
                throw new LocalizedMessageException("Empty response received");
            }

            return response.textMessages().stream().map(
                    msg -> new MessageModelDto(
                            msg.userId(),
                            msg.textData().substring(0, msg.textData().length() - 8),// todo to service :)
                            msg.id(),
                            TIME_FORMATTER.format(
                                    LocalTime.ofInstant(Instant.ofEpochMilli(msg.time()), ZoneId.systemDefault()))
                    )
            ).toList();
        } catch (RestClientException e) {
            throw new LocalizedMessageException(e.getMessage());
        }
    }

    public List<MessageModelDto> pullMessages(String lastKnownId, String chatId, int limit) {
        return pullMessages(null, lastKnownId, chatId, limit);
    }

    public void saveMessage(SendMessageDto sendMessageDto) {
        try {
            TextMessageSendRequest request = new TextMessageSendRequest(sendMessageDto.chatId(),
                    new TextMessage(
                            sendMessageDto.userId(),
                            sendMessageDto.text(),
                            null,
                            null
                    ));

            TextMessageSendResponse response = restTemplate.postForEntity(
                    hostRestProperty.host() + messagesRestInteractionProperty.saveMessage(),
                    request, TextMessageSendResponse.class).getBody();

            if (response == null) {
                throw new LocalizedMessageException("Empty response received");
            }
        } catch (RestClientException e) {
            throw new LocalizedMessageException(e.getMessage());
        }
    }
}
