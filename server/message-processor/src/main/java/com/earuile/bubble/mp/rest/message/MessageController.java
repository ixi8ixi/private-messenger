package com.earuile.bubble.mp.rest.message;

import com.earuile.bubble.mp.core.message.TransformMessageService;
import com.earuile.bubble.mp.rest.message.info.MessageRequest;
import com.earuile.bubble.mp.rest.message.info.MessageResponse;
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
@RequestMapping("/mp/message")
public class MessageController {

    private final TransformMessageService transformMessageService;
    private final MessageMapper mapper;

    @Operation(summary = "Transform message")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Catch the message",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = MessageRequest.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid message format",
            content = @Content)
    })
    @PostMapping("/new")
    public MessageResponse getMessageAndTransform(@RequestBody MessageRequest request) {
        return mapper.mapDtoToResponse(
                transformMessageService.transform(
                        mapper.mapRequestToDto(request)
                )
        );
    }

}
