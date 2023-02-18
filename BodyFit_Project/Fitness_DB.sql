-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema fitnessdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema fitnessdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fitnessdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `fitnessdb` ;

-- -----------------------------------------------------
-- Table `fitnessdb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitnessdb`.`user` (
  `user_seq` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(20) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `nickname` VARCHAR(30) NOT NULL,
  `rank` INT NOT NULL DEFAULT '0',
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `followed_count` INT NULL DEFAULT '0',
  `following_count` INT NULL DEFAULT '0',
  PRIMARY KEY (`user_seq`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `user_seq_UNIQUE` (`user_seq` ASC) VISIBLE,
  UNIQUE INDEX `nickname_UNIQUE` (`nickname` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fitnessdb`.`article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitnessdb`.`article` (
  `article_id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_seq` BIGINT NOT NULL,
  `board_type` INT NOT NULL DEFAULT '0',
  `view_count` BIGINT NULL DEFAULT '0',
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL DEFAULT NULL,
  `title` VARCHAR(255) NOT NULL,
  `content` TEXT NOT NULL,
  PRIMARY KEY (`article_id`),
  UNIQUE INDEX `review_id_UNIQUE` (`article_id` ASC) VISIBLE,
  INDEX `fk_review_user1_idx` (`user_seq` ASC) VISIBLE,
  CONSTRAINT `fk_review_user10`
    FOREIGN KEY (`user_seq`)
    REFERENCES `fitnessdb`.`user` (`user_seq`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fitnessdb`.`article_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitnessdb`.`article_comment` (
  `comment_id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_seq` BIGINT NOT NULL,
  `article_id` BIGINT NOT NULL,
  `content` VARCHAR(255) NOT NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL DEFAULT NULL,
  `group_id` BIGINT NULL DEFAULT NULL,
  `group_order` INT NULL DEFAULT '0',
  `depth` INT NULL DEFAULT '0',
  `original_comment_id` BIGINT NULL DEFAULT '0',  
  `deleted` TINYINT NOT NULL DEFAULT '0',
  PRIMARY KEY (`comment_id`),
  UNIQUE INDEX `comment_id_UNIQUE` (`comment_id` ASC) VISIBLE,
  INDEX `fk_comment_user1_idx` (`user_seq` ASC) VISIBLE,
  INDEX `fk_comment_copy1_article1_idx` (`article_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_copy1_article1`
    FOREIGN KEY (`article_id`)
    REFERENCES `fitnessdb`.`article` (`article_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_user10`
    FOREIGN KEY (`user_seq`)
    REFERENCES `fitnessdb`.`user` (`user_seq`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fitnessdb`.`follow`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitnessdb`.`follow` (
  `follow_id` BIGINT NOT NULL AUTO_INCREMENT,
  `followed_user_seq` BIGINT NOT NULL,
  `following_user_seq` BIGINT NOT NULL,
  PRIMARY KEY (`follow_id`),
  UNIQUE INDEX `follow_id_UNIQUE` (`follow_id` ASC) VISIBLE,
  INDEX `fk_follow_user1_idx` (`followed_user_seq` ASC) VISIBLE,
  INDEX `fk_follow_user2_idx` (`following_user_seq` ASC) VISIBLE,
  CONSTRAINT `fk_follow_user10`
    FOREIGN KEY (`followed_user_seq`)
    REFERENCES `fitnessdb`.`user` (`user_seq`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_follow_user20`
    FOREIGN KEY (`following_user_seq`)
    REFERENCES `fitnessdb`.`user` (`user_seq`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fitnessdb`.`gym`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitnessdb`.`gym` (
  `gym_id` BIGINT NOT NULL AUTO_INCREMENT,
  `gym_name` VARCHAR(255) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  PRIMARY KEY (`gym_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fitnessdb`.`video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitnessdb`.`video` (
  `video_id` BIGINT NOT NULL AUTO_INCREMENT,
  `video_url` VARCHAR(255) NOT NULL,
  `channel` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT NOT NULL,
  `like_count` INT NULL DEFAULT '0',
  `view_count` BIGINT NULL DEFAULT '0',
  `review_count` BIGINT NULL DEFAULT '0',
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL DEFAULT NULL,
  `category` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`video_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fitnessdb`.`like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitnessdb`.`like` (
  `like_id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_seq` BIGINT NOT NULL,
  `video_id` BIGINT NOT NULL,
  PRIMARY KEY (`like_id`),
  UNIQUE INDEX `follow_id_UNIQUE` (`like_id` ASC) VISIBLE,
  INDEX `fk_like_user1_idx` (`user_seq` ASC) VISIBLE,
  INDEX `fk_like_video1_idx` (`video_id` ASC) VISIBLE,
  CONSTRAINT `fk_like_user1`
    FOREIGN KEY (`user_seq`)
    REFERENCES `fitnessdb`.`user` (`user_seq`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_like_video1`
    FOREIGN KEY (`video_id`)
    REFERENCES `fitnessdb`.`video` (`video_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fitnessdb`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitnessdb`.`review` (
  `review_id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_seq` BIGINT NOT NULL,
  `video_id` BIGINT NOT NULL,
  `view_count` BIGINT NULL DEFAULT '0',
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL DEFAULT NULL,
  `title` VARCHAR(255) NOT NULL,
  `content` TEXT NOT NULL,
  PRIMARY KEY (`review_id`),
  UNIQUE INDEX `review_id_UNIQUE` (`review_id` ASC) VISIBLE,
  INDEX `fk_review_video_idx` (`video_id` ASC) VISIBLE,
  INDEX `fk_review_user1_idx` (`user_seq` ASC) VISIBLE,
  CONSTRAINT `fk_review_user1`
    FOREIGN KEY (`user_seq`)
    REFERENCES `fitnessdb`.`user` (`user_seq`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_review_video`
    FOREIGN KEY (`video_id`)
    REFERENCES `fitnessdb`.`video` (`video_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fitnessdb`.`review_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitnessdb`.`review_comment` (
  `comment_id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_seq` BIGINT NOT NULL,
  `review_id` BIGINT NOT NULL,
  `content` VARCHAR(255) NOT NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL DEFAULT NULL,
  `group_id` BIGINT NULL DEFAULT '0',
  `group_order` INT NULL DEFAULT '0',
  `depth` INT NULL DEFAULT '0',
  `original_comment_id` BIGINT NULL DEFAULT '0',  
  `deleted` TINYINT NOT NULL DEFAULT '0',
  PRIMARY KEY (`comment_id`),
  UNIQUE INDEX `comment_id_UNIQUE` (`comment_id` ASC) VISIBLE,
  INDEX `fk_comment_user1_idx` (`user_seq` ASC) VISIBLE,
  INDEX `fk_comment_review1_idx` (`review_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_review1`
    FOREIGN KEY (`review_id`)
    REFERENCES `fitnessdb`.`review` (`review_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_seq`)
    REFERENCES `fitnessdb`.`user` (`user_seq`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


