Drop TABLE IF EXISTS TIMED_KEY_VALUE;
CREATE TABLE TIMED_KEY_VALUE
(
    id      int PRIMARY KEY AUTO_INCREMENT,
    key     varchar(200) NOT NULL,
    content varchar(160) NOT NULL,
    date    timestamp    NOT NULL,
    ttl     int          not null
);
