package com.earuile.bubble.core.rest.interaction;

import com.earuile.bubble.core.rest.config.property.HostRestProperty;
import com.earuile.bubble.core.rest.config.property.UsersRestInteractionProperty;
import com.earuile.bubble.core.rest.dto.*;
import com.earuile.bubble.core.util.LocalizedMessageException;
import com.earuile.bubble.public_interface.RegisterFormDto;
import com.earuile.bubble.public_interface.UserDataDto;
import com.earuile.bubble.public_interface.chat.ChatInfoDto;
import com.earuile.bubble.public_interface.user.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersRestService {
    private final HostRestProperty hostRestProperty;
    private final UsersRestInteractionProperty userRestProperty;
    private final RestTemplate restTemplate;

    /**
     * Create new user on server with specified data.
     *
     * @throws LocalizedMessageException if problems while create
     */
    public UserDataDto createNewUser(RegisterFormDto dto) {
        try {
            UserRegistrationRequest request = mapToRequest(dto);
            UserRegistrationResponse response =
                    restTemplate.postForEntity(hostRestProperty.host() + userRestProperty.createNewUser(),
                            request, UserRegistrationResponse.class).getBody();

            if (response == null) {
                throw new LocalizedMessageException("Empty response received");
            }

            return new UserDataDto(response.userId(), dto.login(), dto.name(), dto.password());
        } catch (RestClientException e) {
            throw new LocalizedMessageException("Error on server: " + e.getLocalizedMessage()); // fixme change error message
        }
    }

    /**
     * Pull all user's chats info from server.
     *
     * @throws LocalizedMessageException if problems while pull
     */
    public List<ChatInfoDto> allUserChats(String id) {
        try {
            UserGetChatsRequest request = new UserGetChatsRequest(id);
            UserGetChatsResponse response =
                    restTemplate.postForEntity(hostRestProperty.host() + userRestProperty.allUserChats(),
                            request, UserGetChatsResponse.class).getBody();

            if (response == null) {
                throw new LocalizedMessageException("Empty response received");
            }

            return response.chats().stream().map(UsersRestService::mapToChatInfoDto).toList();
        } catch (RestClientException e) {
            throw new LocalizedMessageException(e.getLocalizedMessage());
        }
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

    private static ChatInfoDto mapToChatInfoDto(ChatInfo chatInfo) {
        List<UserInfo> list = chatInfo.users();
        List<UserInfoDto> rlist = list == null ? null : list.stream().map(UsersRestService::mapToUserInfoDto).toList();
        return new ChatInfoDto(
                chatInfo.id(),
                chatInfo.name(),
                rlist,
                chatInfo.time()
        );
    }

    private static UserInfoDto mapToUserInfoDto(UserInfo userInfo) {
        return new UserInfoDto(
                userInfo.id(),
                userInfo.login(),
                userInfo.name(),
                userInfo.time()
        );
    }
}
