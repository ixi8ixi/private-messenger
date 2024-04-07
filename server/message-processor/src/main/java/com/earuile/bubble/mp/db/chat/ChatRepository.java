package com.earuile.bubble.mp.db.chat;

import com.earuile.bubble.mp.db.chat.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, String> {

}
