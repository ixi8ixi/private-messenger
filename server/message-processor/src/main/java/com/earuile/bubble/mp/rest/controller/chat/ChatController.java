package com.earuile.bubble.mp.rest.controller.chat;

import com.earuile.bubble.mp.core.service.chat.ChatService;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.add_user.AddUsersToChatRequest;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.add_user.AddUsersToChatResponse;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.chat_info.GetChatInfoRequest;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.chat_info.GetChatInfoResponse;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.create.CreateChatRequest;
import com.earuile.bubble.mp.rest.controller.chat.info.end_point.create.CreateChatResponse;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mp/chat")
public class ChatController {
    private final ChatService chatService;
    private final ChatMapper mapper;

    @Operation(summary = "Create new chat and return its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The creating was correct",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateChatResponse.class,
                                    description = "Chat id and server system information"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request format",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "A user wasn't found for one of the submitted ids",
                    content = @Content),
            @ApiResponse(responseCode = "415", description = "Bad length of arguments",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
    })
    @PostMapping("/creation")
    public CreateChatResponse create(@RequestBody @Valid CreateChatRequest request) {
        return mapper.mapDtoToResponse(
                chatService.create(
                        mapper.mapRequestToDto(request)
                )
        );
    }

    @Operation(summary = "Get all info about chat by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct response",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetChatInfoResponse.class,
                                    description = "Chat info and its users info about all (without theirs chats)"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request format",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Chat not found",
                    content = @Content),
            @ApiResponse(responseCode = "415", description = "Incorrect form of the argument (chatId)",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    })
    @PostMapping("/ch1")
    public GetChatInfoResponse get(@RequestBody @Valid GetChatInfoRequest request) {
        return mapper.mapDtoToResponse(
                chatService.get(
                        mapper.mapRequestToDto(request)
                )
        );
    }

    @Operation(summary = "Updates the chat by adding new users to it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct response",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddUsersToChatResponse.class,
                                    description = "New chat info and its users info"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request format",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Chat not found or any user not found",
                    content = @Content),
            @ApiResponse(responseCode = "415", description = "Incorrect form of the argument (chatId, userId)",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    })
    @PatchMapping("/ch1")
    public AddUsersToChatResponse addUsers(@RequestBody @Valid AddUsersToChatRequest request) {
        return mapper.mapDtoToResponse(
                chatService.addUsers(
                        mapper.mapRequestToDto(request)
                )
        );
    }

}
