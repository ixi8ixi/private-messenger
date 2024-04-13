package com.earuile.bubble.core.rest.interaction;

import com.earuile.bubble.core.db.info.UserInfoService;
import com.earuile.bubble.core.rest.config.property.HostRestProperty;
import com.earuile.bubble.core.rest.config.property.UsersRestInteractionProperty;
import com.earuile.bubble.core.rest.dto.*;
import com.earuile.bubble.core.util.LocalizedMessageException;
import com.earuile.bubble.public_interface.RegisterFormDto;
import com.earuile.bubble.public_interface.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

// todo add exception processing

@Service
@RequiredArgsConstructor
public class UsersRestService {
    private final HostRestProperty hostRestProperty;
    private final UsersRestInteractionProperty property;
    private final RestTemplate restTemplate;

    /**
     * Create new user on server with specified data.
     *
     * @throws LocalizedMessageException if problems while create
     */
    public UserInfoDto createNewUser(RegisterFormDto dto) {
        try {
            UserRegistrationRequest request = mapToRequest(dto);
            UserRegistrationResponse response =
                    restTemplate.postForEntity(hostRestProperty.host() + property.createNewUser(),
                            request, UserRegistrationResponse.class).getBody(); // todo check if null

            if (response == null) {
                throw new LocalizedMessageException("Empty response received");
            }

            return new UserInfoDto(response.userId(), dto.login(), dto.name(), dto.password());
        } catch (RestClientException e) {
            throw new LocalizedMessageException("Error on server: " + e.getLocalizedMessage()); // fixme change error message
        }
    }


    public List<ChatInfo> allUserChats(String id) {
        UserGetChatsRequest request = new UserGetChatsRequest(id);
        UserGetChatsResponse response =
                restTemplate.postForEntity(property.allUserChats(), request, UserGetChatsResponse.class).getBody();

        if (response == null) {
            throw new RuntimeException(); // fixme!
        }

        return response.chats();
    }

    public void getInfo() {
        
    }

    private static UserRegistrationRequest mapToRequest(RegisterFormDto dto) {
        return new UserRegistrationRequest(
                dto.login(),
                dto.password(),
                dto.name()
        );
    }
}
