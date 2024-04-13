package com.earuile.bubble.core;

import com.earuile.bubble.core.rest.interaction.UsersRestService;
import com.earuile.bubble.public_interface.RegisterFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersController {
    private final UsersRestService usersRestService;

    public void register(RegisterFormDto dto) {

    }
}
