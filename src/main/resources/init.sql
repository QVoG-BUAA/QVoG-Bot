/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

DROP TABLE IF EXISTS `requests`;
CREATE TABLE `requests`
(
    `id`             CHAR(16)     NOT NULL,
    `clone_url`      VARCHAR(255) NOT NULL,
    `branch`         VARCHAR(255) NOT NULL,
    `commit_id`      CHAR(40)     NOT NULL,
    `commit_message` TEXT         NOT NULL,
    `author`         VARCHAR(255) NOT NULL,
    `author_email`   VARCHAR(255) NOT NULL,
    `repo_name`      VARCHAR(255) NOT NULL,
    `raw`            TEXT         NOT NULL,
    `created_at`     TIMESTAMP    NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `results`;
CREATE TABLE `results`
(
    `id`          CHAR(16)  NOT NULL,
    `success`     BOOL      NOT NULL,
    `message`     TEXT      NOT NULL,
    `bug_count`   INT       NOT NULL,
    `query_count` INT       NOT NULL,
    `data`        TEXT      NOT NULL,
    `created_at`  TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
)