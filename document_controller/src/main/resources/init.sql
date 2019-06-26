
CREATE TABLE `DOCUMENT` (
  `id` VARCHAR(20) PRIMARY KEY NOT NULL,
  `metadata` CLOB ,
  `length` BIGINT(20) NULL,
  `content` CLOB NOT NULL
);

