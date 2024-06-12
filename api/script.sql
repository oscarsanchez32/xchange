-- Disable unique checks and foreign key checks to avoid issues during table creation
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- Create the gamestore schema
CREATE SCHEMA IF NOT EXISTS `gamestore` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gamestore`;

-- Create the game_detail table
CREATE TABLE IF NOT EXISTS `gamestore`.`game_detail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ean` VARCHAR(255) NULL DEFAULT NULL,
  `language` VARCHAR(255) NULL DEFAULT NULL,
  `publisher` VARCHAR(255) NULL DEFAULT NULL,
  `long_desc` VARCHAR(1024) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 64
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- Create the game table
CREATE TABLE IF NOT EXISTS `gamestore`.`game` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `developera` VARCHAR(45) NULL DEFAULT NULL,
  `img_path` VARCHAR(255) NULL DEFAULT NULL,
  `price` FLOAT NULL DEFAULT NULL,
  `short_desc` VARCHAR(512) NULL DEFAULT NULL,
  `title` VARCHAR(50) NULL DEFAULT NULL,
  `game_detail_id` INT NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `tags` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKio72xbateqjgsts3w2fwwr8l2` (`game_detail_id`),
  CONSTRAINT `FKio72xbateqjgsts3w2fwwr8l2`
    FOREIGN KEY (`game_detail_id`)
    REFERENCES `gamestore`.`game_detail` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 35
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- Create the gamestore_user table
CREATE TABLE IF NOT EXISTS `gamestore`.`gamestore_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `roles` VARCHAR(255) NULL DEFAULT NULL,
  `user_name` VARCHAR(40) NULL DEFAULT NULL,
  `credits` DOUBLE NOT NULL,
  `user_img` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- Create the gamestore_order table
CREATE TABLE IF NOT EXISTS `gamestore`.`gamestore_order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME NULL DEFAULT NULL,
  `payment_type` VARCHAR(255) NULL DEFAULT NULL,
  `total_amt` DOUBLE NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  `status` VARCHAR(255) NULL DEFAULT NULL,
  `receipt_url` VARCHAR(255) NULL DEFAULT NULL,
  `stripe_session_id` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKi54tx5e7b9ue47868gs5egpvr` (`user_id`),
  CONSTRAINT `FKi54tx5e7b9ue47868gs5egpvr`
    FOREIGN KEY (`user_id`)
    REFERENCES `gamestore`.`gamestore_user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 68
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- Create the cart table
CREATE TABLE IF NOT EXISTS `gamestore`.`cart` (
  `game_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  INDEX `FK7f24kmlp6cyjqfriv5rsudgru` (`user_id`),
  INDEX `FK5n0sq8ulj6ykdnrh4dnk0vfmw` (`game_id`),
  CONSTRAINT `FK5n0sq8ulj6ykdnrh4dnk0vfmw`
    FOREIGN KEY (`game_id`)
    REFERENCES `gamestore`.`game` (`id`),
  CONSTRAINT `FK7f24kmlp6cyjqfriv5rsudgru`
    FOREIGN KEY (`user_id`)
    REFERENCES `gamestore`.`gamestore_user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- Create the exchange_board table
CREATE TABLE IF NOT EXISTS `gamestore`.`exchange_board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `closed_at` DATETIME NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `status` VARCHAR(255) NULL DEFAULT NULL,
  `exchange_closer_user_id` INT NULL DEFAULT NULL,
  `exchange_opener_user_id` INT NULL DEFAULT NULL,
  `opener_exchange_game_id` INT NULL DEFAULT NULL,
  `opener_owned_game_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKpfrna9400w5v8d3caw2rbjbjc` (`exchange_closer_user_id`),
  INDEX `FKsflopcx2rooeieupta7lv09h3` (`exchange_opener_user_id`),
  INDEX `FKol6t93jbnngt2ag2u31y3mngy` (`opener_exchange_game_id`),
  INDEX `FKqvn196c0pd6g50xob55mc5o3v` (`opener_owned_game_id`),
  CONSTRAINT `FKol6t93jbnngt2ag2u31y3mngy`
    FOREIGN KEY (`opener_exchange_game_id`)
    REFERENCES `gamestore`.`game` (`id`),
  CONSTRAINT `FKpfrna9400w5v8d3caw2rbjbjc`
    FOREIGN KEY (`exchange_closer_user_id`)
    REFERENCES `gamestore`.`gamestore_user` (`id`),
  CONSTRAINT `FKqvn196c0pd6g50xob55mc5o3v`
    FOREIGN KEY (`opener_owned_game_id`)
    REFERENCES `gamestore`.`game` (`id`),
  CONSTRAINT `FKsflopcx2rooeieupta7lv09h3`
    FOREIGN KEY (`exchange_opener_user_id`)
    REFERENCES `gamestore`.`gamestore_user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12 
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- Create the order_items table
CREATE TABLE IF NOT EXISTS `gamestore`.`order_items` (
  `order_id` INT NOT NULL,
  `game_id` INT NOT NULL,
  INDEX `FK3urcws17ooy0lbjtwi83wf1l6` (`order_id`),
  INDEX `FKqscqcme08spiyt2guyqdj72eh` (`game_id`),
  CONSTRAINT `FK3urcws17ooy0lbjtwi83wf1l6`
    FOREIGN KEY (`order_id`)
    REFERENCES `gamestore`.`gamestore_order` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `FKqscqcme08spiyt2guyqdj72eh`
    FOREIGN KEY (`game_id`)
    REFERENCES `gamestore`.`game` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- Create the promo table
CREATE TABLE IF NOT EXISTS `gamestore`.`promo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `promo_amount` DOUBLE NULL DEFAULT NULL,
  `promo_code` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- Create the review table
CREATE TABLE IF NOT EXISTS `gamestore`.`review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(512) NULL DEFAULT NULL,
  `rating` INT NOT NULL,
  `timestamp` DATETIME NULL DEFAULT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `game_id` INT NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK70yrt09r4r54tcgkrwbeqenbs` (`game_id`),
  INDEX `FKfoea77te8dj4ha0ewkq5iq08p` (`user_id`),
  CONSTRAINT `FK70yrt09r4r54tcgkrwbeqenbs`
    FOREIGN KEY (`game_id`)
    REFERENCES `gamestore`.`game` (`id`),
  CONSTRAINT `FKfoea77te8dj4ha0ewkq5iq08p`
    FOREIGN KEY (`user_id`)
    REFERENCES `gamestore`.`gamestore_user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- Create the user_inventory table
CREATE TABLE IF NOT EXISTS `gamestore`.`user_inventory` (
  `user_id` INT NOT NULL,
  `game_id` INT NOT NULL,
  INDEX `FKn2brxaaekd6wk77u9qrmg5v05` (`game_id`),
  INDEX `FKrc157o6xjq4u1lwo7xgl8dq63` (`user_id`),
  CONSTRAINT `FKn2brxaaekd6wk77u9qrmg5v05`
    FOREIGN KEY (`game_id`)
    REFERENCES `gamestore`.`game` (`id`),
  CONSTRAINT `FKrc157o6xjq4u1lwo7xgl8dq63`
    FOREIGN KEY (`user_id`)
    REFERENCES `gamestore`.`gamestore_user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- Insert initial data into gamestore_user table
INSERT INTO `gamestore`.`gamestore_user`
(`email`,
`first_name`,
`last_name`,
`password`,
`roles`,
`user_name`,
`credits`,
`user_img`)
VALUES
('admin1@test.com',
'John',
'Doe',
'$2y$10$9Ws67k08gucSb9sjL8OYv.lDjPKU6J075idVDqaroCW9S6jthN71a',
'ROLE_ADMIN',
'admin1',
'500',
'https://fastly.picsum.photos/id/314/200/200.jpg?hmac=bCAc2iO5ovLPrvwDQV31aBPS13QTyv33ut2H2wY4QXU');

-- Re-enable unique checks and foreign key checks
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;