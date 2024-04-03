CREATE TABLE initial (
    id INT PRIMARY KEY NOT NULL,
    value NUMERIC(20, 5),
    type VARCHAR NOT NULL check (type IN ('MESSAGE', 'IMAGE'))
);
