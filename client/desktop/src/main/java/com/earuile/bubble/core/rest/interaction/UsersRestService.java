package com.earuile.bubble.core.rest.interaction;

import com.earuile.bubble.core.db.info.UserInfoService;
import com.earuile.bubble.core.rest.config.property.UsersRestInteractionProperty;
import com.earuile.bubble.core.rest.dto.UserGetChatsRequest;
import com.earuile.bubble.core.rest.dto.UserGetChatsResponse;
import com.earuile.bubble.core.rest.dto.UserRegistrationRequest;
import com.earuile.bubble.core.rest.dto.UserRegistrationResponse;
import com.earuile.bubble.public_interface.RegisterFormDto;
import com.earuile.bubble.public_interface.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// todo add exception processing

@Service
@RequiredArgsConstructor
public class UsersRestService {
    private final UsersRestInteractionProperty property;
    private final RestTemplate restTemplate;
    private final UserInfoService userInfoService;

    public void createNewUser(RegisterFormDto dto) {
        UserRegistrationRequest request = mapToRequest(dto);
        UserRegistrationResponse response =
                restTemplate.postForEntity(property.createNewUser(), request, UserRegistrationResponse.class).getBody(); // todo check if null

        if (response == null) {
            throw new RuntimeException(); // fixme!
        }

        UserInfoDto infoDto = new UserInfoDto(response.userId(), dto.login(), dto.name(), dto.password());
        userInfoService.updateInfo(infoDto);
    }

    public void allUserChats() {
        if (userInfoService.loadInfo()) {
            throw new RuntimeException("User info request required"); // fixme
        }

        UserGetChatsRequest request = new UserGetChatsRequest(userInfoService.id());
        UserGetChatsResponse response =
                restTemplate.postForEntity(property.allUserChats(), request, UserGetChatsResponse.class).getBody();
        // todo load user chats
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
