package com.earuile.bubble.mp.db.user;

import com.earuile.bubble.mp.db.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
