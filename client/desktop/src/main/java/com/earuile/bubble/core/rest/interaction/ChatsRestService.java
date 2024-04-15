package com.earuile.bubble.core.rest.interaction;

import com.earuile.bubble.core.rest.config.property.ChatsRestInteractionProperty;
import com.earuile.bubble.core.rest.config.property.HostRestProperty;
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
    private final HostRestProperty hostRestProperty;
    private final ChatsRestInteractionProperty chatsRestInteractionProperty;
    private final RestTemplate restTemplate;

    public void createChat(String creator, String name, List<String> members) {
        try {
            CreateChatRequest request = members == null
                    ? new CreateChatRequest(creator, name, List.of(creator))
                    : new CreateChatRequest(creator, name, members); // todo Мб перенести это на сторону сервера?
                                                                    //    имею в виду, что скорее всего человек хочет находиться в чате, который создаёт
            System.out.println(request);
            CreateChatResponse response = restTemplate.postForEntity(
                    hostRestProperty.host() + chatsRestInteractionProperty.createChat(),
                    request,
                    CreateChatResponse.class
            ).getBody();
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            throw new LocalizedMessageException(e.getMessage());
        }
         // todo add exception processing
    }

    public void appendUsers() {
        // to be implemented...
    }

    public void getInfo() {

    }
}
