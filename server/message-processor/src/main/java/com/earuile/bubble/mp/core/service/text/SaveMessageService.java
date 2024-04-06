package com.earuile.bubble.mp.core.service.text;

import com.earuile.bubble.mp.public_interface.text.dto.TextSendRequestDto;
import com.earuile.bubble.mp.public_interface.text.dto.TextSendResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveMessageService {

    public TextSendResponseDto save(TextSendRequestDto requestDto) {

        return TextSendResponseDto.builder()
                .time(Long.toString(System.currentTimeMillis()))
                .build();
    }

}
