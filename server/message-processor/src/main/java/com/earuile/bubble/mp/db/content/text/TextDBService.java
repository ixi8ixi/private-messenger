package com.earuile.bubble.mp.db.content.text;

import com.earuile.bubble.mp.db.content.text.entity.TextEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TextDBService {
    private final TextRepository repository;

    public TextEntity createAndGet(String text) {
        return repository.save(TextEntity.builder()
                .text(text)
                .build()
        );
    }

    public Optional<TextEntity> getTextById(long id) {
        return repository.findById(id);
    }
}
