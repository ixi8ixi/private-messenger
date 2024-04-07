package com.earuile.bubble.mp.db.user;

import com.earuile.bubble.mp.db.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDBService {
    private final UserRepository repository;

    public UserEntity createAndGet(UserEntity user) {
        return repository.save(user);
    }

    public Optional<UserEntity> getById(String id) {
        return repository.findById(id);
    }

}
