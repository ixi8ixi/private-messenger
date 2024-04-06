package com.earuile.bubble.rest;

import com.earuile.bubble.rest.config.MessengerServerRestProperty;
import com.earuile.bubble.rest.dto.MessageRequest;
import com.earuile.bubble.rest.dto.MessageResponse;
import com.earuile.bubble.rest.dto.TextMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SendMessageRestService {
    private final RestTemplate restTemplate;
    private final TaskExecutor taskExecutor;
    private final MessengerServerRestProperty messengerServerRestProperty;

    public void sendMessage(String userId, String chatId, String message) {
        taskExecutor.execute(() -> {
            HttpEntity<MessageRequest> request = new HttpEntity<>(new MessageRequest(userId, chatId,
                    new TextMessage(message, null)));
            restTemplate.postForEntity(messengerServerRestProperty.uri(), request, MessageResponse.class);
        });
    }

    public void pullMessages() {

    }
}
