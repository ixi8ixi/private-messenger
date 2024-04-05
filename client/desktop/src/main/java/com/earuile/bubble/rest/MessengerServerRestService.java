package com.earuile.bubble.rest;

import com.earuile.bubble.rest.dto.MessageRequest;
import com.earuile.bubble.rest.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MessengerServerRestService {
    private final RestTemplate restTemplate;

    public String sendMessage(String message) {
        HttpEntity<MessageRequest> request = new HttpEntity<>(new MessageRequest(message));
        ResponseEntity<MessageResponse> response = restTemplate
                .postForEntity("PLACEHOLDER", request, MessageResponse.class);
        return response.getBody().message();
    }
}
