package com.earuile.bubble.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SendMessageRestService {
    private final RestTemplate restTemplate;
    private final TaskExecutor taskExecutor;

    public void sendMessage(String message) {
        taskExecutor.execute(() -> {

        });
//        HttpEntity<MessageRequest> request = new HttpEntity<>(new MessageRequest(message));
//        ResponseEntity<MessageResponse> response = restTemplate
//                .postForEntity("http://192.168.31.54:8082/mp/message/new", request, MessageResponse.class);
//        return response.getBody().message();
    }
}
