CREATE TABLE users (
    id VARCHAR(39) NOT NULL PRIMARY KEY,
    login VARCHAR(20) NOT NULL,
    name VARCHAR(20) NOT NULL,
    time TIMESTAMP NOT NULL
);

CREATE TABLE chats (
    id VARCHAR(39) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    time TIMESTAMP NOT NULL
);

CREATE TABLE messages (
    id           VARCHAR(40) NOT NULL PRIMARY KEY,
    user_id      VARCHAR(39) NOT NULL,
    chat_id      VARCHAR(39) NOT NULL,
    time         TIMESTAMP   NOT NULL,
    content_type VARCHAR     NOT NULL check (content_type IN ('TEXT', 'IMAGE', 'VIDEO')),
    content      BIGINT      NOT NULL
);
