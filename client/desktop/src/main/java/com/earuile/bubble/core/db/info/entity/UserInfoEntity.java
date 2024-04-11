package com.earuile.bubble.core.db.info.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "user_info")
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoEntity {
    @Id
    String id;
    String login;
    String name;
    String password;
}


