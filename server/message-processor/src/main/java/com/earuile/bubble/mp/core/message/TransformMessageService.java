package com.earuile.bubble.mp.core.message;

import com.earuile.bubble.mp.public_interface.MessageRequestDto;
import com.earuile.bubble.mp.public_interface.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransformMessageService {

    public MessageResponseDto transform(MessageRequestDto requestDto) {
        return MessageResponseDto.builder()
                .message(requestDto.message() + ", hello!")
                .build();
    }

}
