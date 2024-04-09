CREATE TABLE mp_user
(
    id       VARCHAR(39)  NOT NULL PRIMARY KEY DEFAULT concat('US-'::varchar, gen_random_uuid()::varchar),
    login    VARCHAR(20)  NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    name     VARCHAR(20)  NOT NULL,
    time     TIMESTAMP    NOT NULL
);

CREATE TABLE mp_chat
(
    id   VARCHAR(39)  NOT NULL PRIMARY KEY DEFAULT concat('CH-'::varchar, gen_random_uuid()::varchar),
    name VARCHAR(50) NOT NULL,
    time TIMESTAMP    NOT NULL
);

CREATE TABLE mp_user_to_chat
(
    user_id VARCHAR(39) NOT NULL,
    chat_id VARCHAR(39) NOT NULL,
    PRIMARY KEY (user_id, chat_id),
    CONSTRAINT user_chat_ibfk_1 FOREIGN KEY (user_id) REFERENCES mp_user (id),
    CONSTRAINT user_chat_ibfk_2 FOREIGN KEY (chat_id) REFERENCES mp_chat (id)
);

CREATE TABLE mp_message
(
    id           VARCHAR(40) NOT NULL PRIMARY KEY DEFAULT concat('MES-'::varchar, gen_random_uuid()::varchar),
    user_id      VARCHAR(39) NOT NULL REFERENCES mp_user (id),
    chat_id      VARCHAR(39) NOT NULL REFERENCES mp_chat (id),
    time         TIMESTAMP   NOT NULL,
    content_type VARCHAR     NOT NULL check (content_type IN ('TEXT', 'IMAGE', 'VIDEO')),
    content      BIGINT      NOT NULL
);

CREATE TABLE mp_text_message
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    text TEXT                  NOT NULL
);
