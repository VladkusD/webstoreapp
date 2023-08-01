-- MySQL Script generated by MySQL Workbench
-- Tue Aug  1 13:52:20 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema web_shop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema web_shop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `web_shop` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `web_shop` ;

-- -----------------------------------------------------
-- Table `web_shop`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_first_name` VARCHAR(255) NULL DEFAULT NULL,
  `user_last_name` VARCHAR(255) NULL DEFAULT NULL,
  `user_phone` VARCHAR(255) NULL DEFAULT NULL,
  `user_email` VARCHAR(255) NULL DEFAULT NULL,
  `user_address` VARCHAR(255) NULL DEFAULT NULL,
  `user_status` INT NULL DEFAULT NULL,
  `user_password` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_shop`.`web_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop`.`web_order` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `order_status` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `web_order_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `web_shop`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_shop`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop`.`products` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(255) NULL DEFAULT NULL,
  `product_desc` VARCHAR(255) NULL DEFAULT NULL,
  `product_price` DOUBLE NULL DEFAULT NULL,
  `product_image` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`product_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 38
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_shop`.`order_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop`.`order_item` (
  `order_item_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NULL DEFAULT NULL,
  `product_id` INT NULL DEFAULT NULL,
  `product_quantity` INT NULL DEFAULT NULL,
  PRIMARY KEY (`order_item_id`),
  INDEX `order_id` (`order_id` ASC) VISIBLE,
  INDEX `product_id` (`product_id` ASC) VISIBLE,
  CONSTRAINT `order_item_ibfk_1`
    FOREIGN KEY (`order_id`)
    REFERENCES `web_shop`.`web_order` (`order_id`),
  CONSTRAINT `order_item_ibfk_2`
    FOREIGN KEY (`product_id`)
    REFERENCES `web_shop`.`products` (`product_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_shop`.`system_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop`.`system_roles` (
  `role_id` INT NOT NULL,
  `role_description` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_shop`.`user_right`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop`.`user_right` (
  `user_id` INT NOT NULL,
  `role_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `role_id` (`role_id` ASC) VISIBLE,
  CONSTRAINT `user_right_ibfk_1`
    FOREIGN KEY (`role_id`)
    REFERENCES `web_shop`.`system_roles` (`role_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_shop`.`user_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop`.`user_status` (
  `user_status` INT NULL DEFAULT NULL,
  `status_description` VARCHAR(30) NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;