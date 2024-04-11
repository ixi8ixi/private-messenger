package com.earuile.bubble.core.rest.interaction;

import com.earuile.bubble.core.rest.dto.UserGetChatsResponse;
import com.earuile.bubble.core.rest.dto.UserRegistrationRequest;
import com.earuile.bubble.public_interface.RegisterFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// todo add exception processing

@Service
@RequiredArgsConstructor
public class UsersRestService {
    private final RestTemplate restTemplate;

    public void createNewUser(RegisterFormDto dto) {
        UserRegistrationRequest request = mapToRequest(dto);
        UserGetChatsResponse response = restTemplate.postForEntity("", request, UserGetChatsResponse.class).getBody();
    }

    public void allUserChats() {

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
