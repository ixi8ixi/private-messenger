package com.earuile.bubble.mp.rest.text;

import com.earuile.bubble.mp.core.service.text.TextMessageService;
import com.earuile.bubble.mp.rest.text.info.end_point.get.TextMessageGetRequest;
import com.earuile.bubble.mp.rest.text.info.end_point.get.TextMessageGetResponse;
import com.earuile.bubble.mp.rest.text.info.end_point.send.TextMessageSendRequest;
import com.earuile.bubble.mp.rest.text.info.end_point.send.TextMessageSendResponse;
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
public class TextMessageController {

    private final TextMessageService textMessageService;
    private final TextMessageMapper mapper;

    @Operation(summary = "Save message on server and send it to all its chat users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The message was received and processed correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TextMessageSendResponse.class,
                                    description = "Server system information"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request format",
                    content = @Content)
    })
    @PostMapping("/ch1")
    public TextMessageSendResponse sendMessage(@RequestBody TextMessageSendRequest request) {
        return mapper.mapDtoToResponse(
                textMessageService.save(
                        mapper.mapRequestToDto(request)
                )
        );
    }

    @Operation(summary = "Get messages from server")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The messages were found correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TextMessageSendResponse.class,
                                    description = "Encoded messages"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request format",
                    content = @Content)
    })
    @GetMapping("/ch1")
    public TextMessageGetResponse getMessage(@RequestBody TextMessageGetRequest request) {
        return mapper.mapDtoToResponse(
                textMessageService.get(
                        mapper.mapRequestToDto(request)
                )
        );
    }

}
