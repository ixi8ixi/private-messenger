package com.earuile.bubble.core.rest.interaction;

import com.earuile.bubble.core.rest.config.property.ChatsRestInteractionProperty;
import com.earuile.bubble.core.rest.dto.CreateChatRequest;
import com.earuile.bubble.core.rest.dto.CreateChatResponse;
import com.earuile.bubble.core.util.LocalizedMessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatsRestService {
    private final ChatsRestInteractionProperty chatsRestInteractionProperty;
    private final RestTemplate restTemplate;

    public void createChat(String creator, String name, List<String> members) {
        try {
            CreateChatRequest request = new CreateChatRequest(creator, name, members);
            restTemplate.postForEntity(
                    chatsRestInteractionProperty.createChat(),
                    request,
                    CreateChatResponse.class
            ).getBody();
        } catch (RestClientException e) {
            throw new LocalizedMessageException(e.getMessage());
        }
    }

    public void appendUsers() {
        // todo to be implemented...
    }

    public void getInfo() {
        // todo to be implemented...
    }
}
