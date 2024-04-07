package com.earuile.bubble.mp.db.message;

import com.earuile.bubble.mp.db.message.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, String> {
    
}
