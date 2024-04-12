package com.earuile.bubble.mp.rest.controller.user;

import com.earuile.bubble.mp.core.service.user.UserService;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.chat.UserGetChatsRequest;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.chat.UserGetChatsResponse;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.register.UserRegistrationRequest;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.register.UserRegistrationResponse;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.user_info.GetUserInfoRequest;
import com.earuile.bubble.mp.rest.controller.user.info.end_point.user_info.GetUserInfoResponse;
import com.earuile.bubble.mp.rest.handler.info.ValidationErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mp/user")
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;

    @Operation(summary = "Create new user and return its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The registration was correct",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRegistrationResponse.class,
                                    description = "User id and server system information"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request format",
                    content = @Content),
            @ApiResponse(responseCode = "415", description = "Bad length of arguments",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Logic incorrect arguments (the login is already in use)",
                    content = @Content)
    })
    @PostMapping("/registration")
    public UserRegistrationResponse register(@RequestBody @Valid UserRegistrationRequest request) {
        return mapper.mapDtoToResponse(
                userService.register(
                        mapper.mapRequestToDto(request)
                )
        );
    }

    @Operation(summary = "Get all the user's chats")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct response",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserGetChatsResponse.class,
                                    description = "User id and server system information"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request format",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "415", description = "Incorrect form of the argument (userId)",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    })
    @PostMapping("/chats")
    public UserGetChatsResponse getChats(@RequestBody @Valid UserGetChatsRequest request) {
        return mapper.mapDtoToResponse(
                userService.getChats(
                        mapper.mapRequestToDto(request)
                )
        );
    }

    @Operation(summary = "Get all info about user by id or login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct response",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUserInfoResponse.class,
                                    description = "User info"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request format",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "415", description = "Incorrect form of the argument (id or login is bad) or both are nulls",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    })
    @PostMapping("/ch1")
    public GetUserInfoResponse get(@RequestBody @Valid GetUserInfoRequest request) {
        return mapper.mapDtoToResponse(
                userService.get(
                        mapper.mapRequestToDto(request)
                )
        );
    }

}
