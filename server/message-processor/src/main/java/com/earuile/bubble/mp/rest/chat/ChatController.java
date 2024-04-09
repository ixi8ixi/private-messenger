package com.earuile.bubble.mp.rest.chat;

import com.earuile.bubble.mp.core.service.chat.ChatService;
import com.earuile.bubble.mp.rest.chat.info.end_point.create.CreateChatRequest;
import com.earuile.bubble.mp.rest.chat.info.end_point.create.CreateChatResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                    content = @Content),
    })
    @PostMapping("/creation")
    public CreateChatResponse create(@RequestBody CreateChatRequest request) {
        return mapper.mapDtoToResponse(
                chatService.create(
                        mapper.mapRequestToDto(request)
                )
        );
    }

}