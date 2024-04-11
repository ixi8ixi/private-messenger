package com.earuile.bubble.core.db.info;

import com.earuile.bubble.core.db.info.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String> {
}
