package com.earuile.bubble.mp.rest.message;

import com.earuile.bubble.mp.public_interface.MessageRequestDto;
import com.earuile.bubble.mp.public_interface.MessageResponseDto;
import com.earuile.bubble.mp.rest.message.info.MessageRequest;
import com.earuile.bubble.mp.rest.message.info.MessageResponse;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageResponse mapDtoToResponse(MessageResponseDto responseDto) {
        return MessageResponse.builder()
                .message(responseDto.message())
                .build();
    }

    public MessageRequestDto mapRequestToDto(MessageRequest request) {
        return MessageRequestDto.builder()
                .message(request.message())
                .build();
    }

}
