package com.earuile.bubble.mp.db.user;

import com.earuile.bubble.mp.db.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findFirstByLogin(String login);

}
