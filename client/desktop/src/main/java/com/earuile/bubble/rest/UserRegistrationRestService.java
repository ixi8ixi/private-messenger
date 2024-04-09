package com.earuile.bubble.rest;

import com.earuile.bubble.rest.config.MessengerServerRegistrationProperty;
import com.earuile.bubble.public_interface.RegisterFormDto;
import com.earuile.bubble.rest.dto.UserRegistrationRequest;
import com.earuile.bubble.rest.dto.UserRegistrationResponse;
import com.earuile.bubble.ui.controllers.RegistrationReadyCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserRegistrationRestService {
    private final RestTemplate restTemplate;
    private final TaskExecutor threadPoolTaskExecutor;
    private final MessengerServerRegistrationProperty messengerServerRegistrationProperty;

    public void register(RegisterFormDto registerFormDto, RegistrationReadyCallback callback) {
        threadPoolTaskExecutor.execute(() -> {
            UserRegistrationResponse response = restTemplate.postForEntity(
                    messengerServerRegistrationProperty.uri(),
                    mapToRequest(registerFormDto),
                    UserRegistrationResponse.class
            ).getBody(); // todo catch exceptions

            if (response == null) {
                throw new RuntimeException(); // fixme
            }

            callback.enter();
        });
    }

    private static UserRegistrationRequest mapToRequest(RegisterFormDto registerFormDto) {
        return new UserRegistrationRequest(
                registerFormDto.login(),
                registerFormDto.password(),
                registerFormDto.name()
        );
    }
}
