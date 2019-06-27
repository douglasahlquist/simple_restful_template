CREATE DATABASE `challenge`;
USE `challenge`;

DROP TABLE `challenge`.`document`;

CREATE TABLE `challenge`.`document` (
  `id` VARCHAR(20) PRIMARY KEY NOT NULL,
  `metadata` TEXT NULL,
  `length` BIGINT(20) NULL,
  `content` TEXT NOT NULL
) ENGINE=INNODB;

