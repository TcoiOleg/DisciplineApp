CREATE SCHEMA `disciplinedb` ;

CREATE TABLE IF NOT EXISTS `disciplinedb`.`user` (
  `id` BIGINT(20) NOT NULL,
  `create_when` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `login` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `first_name` VARCHAR(45) NULL,
  `second_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `task_score` INT NULL,
  `habit_score` INT NULL,
  `score` INT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `disciplinedb`.`period`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `disciplinedb`.`period` (
  `id` INT NOT NULL,
  `day` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `disciplinedb`.`habit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `disciplinedb`.`habit` (
  `id` BIGINT(20) NOT NULL,
  `name` VARCHAR(45) NULL,
  `difficulty` INT NULL,
  `user_id` BIGINT(20) NOT NULL,
  `create_when` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` LONGTEXT NULL,
  `period_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_habit_user_idx` (`user_id` ASC),
  INDEX `fk_habit_period1_idx` (`period_id` ASC),
  CONSTRAINT `fk_habit_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `disciplinedb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_habit_period1`
    FOREIGN KEY (`period_id`)
    REFERENCES `disciplinedb`.`period` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `disciplinedb`.`history_habit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `disciplinedb`.`history_habit` (
  `id` BIGINT(20) NOT NULL,
  `habit_id` BIGINT(20) NOT NULL,
  `name` VARCHAR(45) NULL,
  `difficulty` INT NULL,
  `description` LONGTEXT NULL,
  `completed` TINYINT(1) NULL,
  `completness_comment` MEDIUMTEXT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_history_habit_habit1_idx` (`habit_id` ASC),
  CONSTRAINT `fk_history_habit_habit1`
    FOREIGN KEY (`habit_id`)
    REFERENCES `disciplinedb`.`habit` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `disciplinedb`.`task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `disciplinedb`.`task` (
  `id` BIGINT(20) NOT NULL,
  `name` VARCHAR(45) NULL,
  `difficulty` INT NULL,
  `created_when` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `description` LONGTEXT NULL,
  `remind_date` DATE NULL,
  `time_date` TIMESTAMP NULL,
  `user_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_task_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_task_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `disciplinedb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `disciplinedb`.`history_task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `disciplinedb`.`history_task` (
  `id` BIGINT(20) NOT NULL,
  `name` VARCHAR(45) NULL,
  `difficulty` INT NULL,
  `description` LONGTEXT NULL,
  `remind_date` DATE NULL,
  `time_date` TIMESTAMP NULL,
  `task_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_history_task_task1_idx` (`task_id` ASC),
  CONSTRAINT `fk_history_task_task1`
    FOREIGN KEY (`task_id`)
    REFERENCES `disciplinedb`.`task` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;