package database;

import static database.Constants.Tables.*;

public class SQLTableCreationFactory {
    public String getCreateSQLForTable(String table) {
        return switch (table) {
            case CLIENT -> "CREATE TABLE IF NOT EXISTS `client` (" +
                    "  `id` bigint NOT NULL AUTO_INCREMENT," +
                    "  `cardNumber` varchar(45) NOT NULL," +
                    "  `fullName` varchar(45) NOT NULL," +
                    "  `pnc` varchar(45) NOT NULL," +
                    "  `address` varchar(45) NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE KEY `id_UNIQUE` (`id`)," +
                    "  UNIQUE KEY `cardNumber_UNIQUE` (`cardNumber`)," +
                    "  UNIQUE KEY `pnc_UNIQUE` (`pnc`)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            case ACCOUNT -> "CREATE TABLE IF NOT EXISTS `account` (" +
                    "  `id` bigint NOT NULL AUTO_INCREMENT," +
                    "  `type` varchar(45) NOT NULL," +
                    "  `balance` float NOT NULL," +
                    "  `dateOfCreation` date NOT NULL," +
                    "  `clientID` bigint NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE KEY `account_id_uindex` (`id`)," +
                    "  KEY `client_idx` (`clientID`)," +
                    "  CONSTRAINT `client` FOREIGN KEY (`clientID`) REFERENCES `client` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            case USER -> "CREATE TABLE IF NOT EXISTS user (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  username VARCHAR(200) NOT NULL," +
                    "  password VARCHAR(64) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  UNIQUE INDEX username_UNIQUE (username ASC));";
            case ROLE -> "CREATE TABLE IF NOT EXISTS role (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  role VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  UNIQUE INDEX role_UNIQUE (role ASC));";
            case USER_ROLE -> "CREATE TABLE IF NOT EXISTS user_role (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  user_id INT NOT NULL," +
                    "  role_id INT NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  INDEX user_id_idx (user_id ASC)," +
                    "  INDEX role_id_idx (role_id ASC)," +
                    "  CONSTRAINT user_fkid" +
                    "    FOREIGN KEY (user_id)" +
                    "    REFERENCES user (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE," +
                    "  CONSTRAINT role_fkid" +
                    "    FOREIGN KEY (role_id)" +
                    "    REFERENCES role (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE);";
            case ACTION -> "CREATE TABLE `action` (" +
                    "  `id` bigint NOT NULL AUTO_INCREMENT," +
                    "  `type` varchar(45) NOT NULL," +
                    "  `details` varchar(100) NOT NULL," +
                    "  `date` date NOT NULL," +
                    "  `employeeID` int NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE KEY `id_UNIQUE` (`id`)," +
                    "  KEY `user_idx` (`employeeID`)," +
                    "  CONSTRAINT `user` FOREIGN KEY (`employeeID`) REFERENCES `user` (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            default -> "";
        };
    }
}
