package com.earuile.bubble.db.info.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "user_info")
public class UserInfoEntity {
    @Id
    String id;
    String login;
    String name;
    String password;
}


