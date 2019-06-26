CREATE DATABASE `challenge`;
USE `challenge`;

DROP TABLE `challenge`.`document`;

CREATE TABLE `challenge`.`document` (
  `id` BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `filename` VARCHAR(256) NULL,
  `length` BIGINT(20) NULL,
  `content` TEXT NOT NULL
) ENGINE=INNODB;

