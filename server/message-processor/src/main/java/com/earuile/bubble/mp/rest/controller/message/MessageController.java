package com.earuile.bubble.mp.rest.controller.message;

import com.earuile.bubble.mp.core.service.message.MessageService;
import com.earuile.bubble.mp.core.service.message.text.TextMessageService;
import com.earuile.bubble.mp.rest.controller.message.info.end_point.get.MessageGetRequest;
import com.earuile.bubble.mp.rest.controller.message.info.end_point.get.MessageGetResponse;
import com.earuile.bubble.mp.rest.controller.message.text.info.end_point.send.TextMessageSendRequest;
import com.earuile.bubble.mp.rest.controller.message.text.info.end_point.send.TextMessageSendResponse;
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
@RequestMapping("/mp/message")
public class MessageController {
    private final MessageService messageService;
    private final TextMessageService textMessageService;
    private final MessageMapper mapper;

    @Operation(summary = "Save message on server and send it to all its chat users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The message was received and processed correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TextMessageSendResponse.class,
                                    description = "Server system information"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request format",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Chat not found",
                    content = @Content),
            @ApiResponse(responseCode = "415", description = "Incorrect form of the argument (chatId)",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    })
    @PostMapping("/to")
    public TextMessageSendResponse sendTextMessage(@RequestBody @Valid TextMessageSendRequest request) {
        return mapper.mapDtoToResponse(
                textMessageService.save(
                        mapper.mapRequestToDto(request)
                )
        );
    }

    @Operation(summary = "Get limit count messages from server after lastKnownId (or from start if it's null)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The messages were found correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageGetResponse.class,
                                    description = "Encoded messages"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request format",
                    content = @Content),
            @ApiResponse(responseCode = "415", description = "Incorrect form of the argument (chatId or userId)",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    })
    @PostMapping("/ch1")
    public MessageGetResponse getMessages(@RequestBody @Valid MessageGetRequest request) {
        return mapper.mapDtoToResponse(
                messageService.get(
                        mapper.mapRequestToDto(request)
                )
        );
    }

}
