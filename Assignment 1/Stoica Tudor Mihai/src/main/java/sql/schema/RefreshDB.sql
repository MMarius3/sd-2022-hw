drop database {DB};
create database {DB};

CREATE TABLE {DB}.`Regular_User` ( `id` BIGINT NOT NULL AUTO_INCREMENT , `user_id` BIGINT NOT NULL , PRIMARY KEY (`id`), INDEX (`user_id`)) ENGINE = InnoDB;
CREATE TABLE {DB}.`Currency` ( `id` BIGINT NOT NULL AUTO_INCREMENT , `code` VARCHAR(5) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;
CREATE TABLE {DB}.`User_Action` ( `id` BIGINT NOT NULL AUTO_INCREMENT , `user_id` BIGINT NOT NULL , `action` VARCHAR(255) NOT NULL , `action_details` VARCHAR(255) NOT NULL , `date` DATE NOT NULL, PRIMARY KEY (`id`), INDEX (`user_id`)) ENGINE = InnoDB;
CREATE TABLE {DB}.`Client` ( `id` BIGINT NOT NULL AUTO_INCREMENT , `name` VARCHAR(255) NOT NULL , `identity_card_number` VARCHAR(255) NOT NULL , `personal_numeric_code` VARCHAR(255) NOT NULL , `address` VARCHAR(255) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;
CREATE TABLE {DB}.`Transaction` ( `id` BIGINT NOT NULL AUTO_INCREMENT , `sum` FLOAT NOT NULL , `date` DATE NOT NULL , `sender_account_id` BIGINT NOT NULL , `receiver_account_id` BIGINT NOT NULL , PRIMARY KEY (`id`), INDEX (`sender_account_id`), INDEX (`receiver_account_id`)) ENGINE = InnoDB;
CREATE TABLE {DB}.`Account` ( `id` BIGINT NOT NULL AUTO_INCREMENT , `client_id` BIGINT NOT NULL , `identification_number` VARCHAR(255) NOT NULL , `sum` FLOAT NOT NULL , `currency_id` BIGINT NOT NULL , `creation_date` DATE NOT NULL , PRIMARY KEY (`id`), INDEX (`client_id`), INDEX (`currency_id`)) ENGINE = InnoDB;
CREATE TABLE {DB}.`User` ( `id` BIGINT NOT NULL AUTO_INCREMENT , `name` VARCHAR(255) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;
CREATE TABLE {DB}.`Administrator_User` ( `id` BIGINT NOT NULL AUTO_INCREMENT , `user_id` BIGINT NOT NULL , PRIMARY KEY (`id`), INDEX (`user_id`)) ENGINE = InnoDB;
ALTER TABLE `Account` ADD CONSTRAINT `Account_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `Client`(`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `Account` ADD CONSTRAINT `Account_ibfk_2` FOREIGN KEY (`currency_id`) REFERENCES `Currency`(`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `Administrator_User` ADD CONSTRAINT `Administrator_User_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User`(`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `Regular_User` ADD CONSTRAINT `Regular_User_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User`(`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `Transaction` ADD CONSTRAINT `Transaction_ibfk_2` FOREIGN KEY (`sender_account_id`) REFERENCES `Account`(`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `Transaction` ADD CONSTRAINT `Transaction_ibfk_3` FOREIGN KEY (`receiver_account_id`) REFERENCES `Account`(`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `User_Action` ADD CONSTRAINT `User_Action_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User`(`id`) ON DELETE CASCADE ON UPDATE CASCADE;

INSERT INTO `Client` (`id`, `name`, `identity_card_number`, `personal_numeric_code`, `address`) VALUES (NULL, 'name0', 'identity_card_number0', 'personal_numeric_code0', 'address0'), (NULL, 'name1', 'identity_card_number1', 'personal_numeric_code1', 'address1'), (NULL, 'name2', 'identity_card_number2', 'personal_numeric_code2', 'address2');
INSERT INTO `Currency` (`id`, `code`) VALUES (NULL, 'RON'), (NULL, 'USD'), (NULL, 'EUR'), (NULL, 'XAU'), (NULL, 'XAG');
INSERT INTO `User` (`id`, `name`) VALUES (NULL, 'user0'), (NULL, 'user1'), (NULL, 'user2'), (NULL, 'user3');
INSERT INTO `Account` (`id`, `client_id`, `identification_number`, `sum`, `currency_id`, `creation_date`) VALUES (NULL, '1', 'identification_number0', '513232', '1', '2022-03-01'), (NULL, '1', 'identification_number1', '531513', '2', '2022-02-10'), (NULL, '1', 'identification_number2', '1233', '3', '2022-01-12'), (NULL, '2', 'identification_number3', '12', '3', '2021-08-19'), (NULL, '2', 'identification_number4', '53125321', '2', '2021-04-22'), (NULL, '3', 'identification_number5', '532', '4', '2022-03-16'), (NULL, '3', 'identification_number6', '12', '5', '2022-03-13');
INSERT INTO `Administrator_User` (`id`, `user_id`) VALUES (NULL, '3'), (NULL, '4');
INSERT INTO `Regular_User` (`id`, `user_id`) VALUES (NULL, '1'), (NULL, '2');
INSERT INTO `Transaction` (`id`, `sum`, `date`, `sender_account_id`, `receiver_account_id`) VALUES (NULL, '1', '2021-05-19', '2', '5'), (NULL, '2', '2021-03-09', '4', '3');
INSERT INTO `User_Action` (`id`, `user_id`, `action`, `action_details`, `date`) VALUES (NULL, '1', 'lovit', '111', '2022-03-08'), (NULL, '1', 'ars', '222', '2022-03-08');












