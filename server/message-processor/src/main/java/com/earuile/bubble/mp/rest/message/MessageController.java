package com.earuile.bubble.mp.rest.message;

import com.earuile.bubble.mp.core.service.message.MessageService;
import com.earuile.bubble.mp.core.service.message.text.TextMessageService;
import com.earuile.bubble.mp.rest.message.text.info.end_point.send.TextMessageSendResponse;
import com.earuile.bubble.mp.rest.message.info.end_point.get.MessageGetRequest;
import com.earuile.bubble.mp.rest.message.info.end_point.get.MessageGetResponse;
import com.earuile.bubble.mp.rest.message.text.info.end_point.send.TextMessageSendRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                    content = @Content)
    })
    @PostMapping("/ch1")
    public TextMessageSendResponse sendTextMessage(@RequestBody TextMessageSendRequest request) {
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
                            schema = @Schema(implementation = TextMessageSendResponse.class,
                                    description = "Encoded messages"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request format",
                    content = @Content),
            @ApiResponse(responseCode = "415", description = "Incorrect form of the argument (chatId or userId)",
                    content = @Content)
    })
    @GetMapping("/ch1")
    public MessageGetResponse getMessages(@RequestBody MessageGetRequest request) {
        return mapper.mapDtoToResponse(
                messageService.get(
                        mapper.mapRequestToDto(request)
                )
        );
    }

}
