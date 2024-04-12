package com.earuile.bubble.mp.db.message.text;

import com.earuile.bubble.mp.db.message.text.entity.TextEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<TextEntity, Long> {

}
