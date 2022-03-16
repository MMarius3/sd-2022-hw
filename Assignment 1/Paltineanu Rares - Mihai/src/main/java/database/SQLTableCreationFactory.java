package database;

import static database.Constants.Tables.*;

public class SQLTableCreationFactory {

    public String getCreateSQLForTable(String table) {
        return switch (table) {
            case USER -> "CREATE TABLE IF NOT EXISTS user (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  username VARCHAR(200) NOT NULL," +
                    "  password VARCHAR(64) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  UNIQUE INDEX username_UNIQUE (username ASC));";
            case CLIENT -> "CREATE TABLE IF NOT EXISTS client (" +
                    "  id int(11) NOT NULL AUTO_INCREMENT," +
                    "  name varchar(100) NOT NULL," +
                    "  CNP varchar(13) NULL," +
                    "  address varchar(100) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)" +
                    ");";

            case ACCOUNT -> "CREATE TABLE IF NOT EXISTS account (" +
                    "  id int(11) NOT NULL AUTO_INCREMENT," +
                    "  number varchar(16) NOT NULL," +
                    "  type varchar(30) NOT NULL," +
                    "  money int(13) NOT NULL," +
                    "  creation DATE NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)" +
                    ");";

            case CLIENT_ACCOUNT -> "  CREATE TABLE IF NOT EXISTS client_account (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  client_id INT NOT NULL," +
                    "  account_id INT NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  INDEX role_id_idx (client_id ASC)," +
                    "  INDEX right_id_idx (account_id ASC)," +
                    "  CONSTRAINT client_id" +
                    "    FOREIGN KEY (client_id)" +
                    "    REFERENCES client (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE," +
                    "  CONSTRAINT account_id" +
                    "    FOREIGN KEY (account_id)" +
                    "    REFERENCES `account` (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE);";

            case ROLE -> "  CREATE TABLE IF NOT EXISTS role (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  role VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  UNIQUE INDEX role_UNIQUE (role ASC));";
            case RIGHT -> "  CREATE TABLE IF NOT EXISTS `right` (" +
                    "  `id` INT NOT NULL AUTO_INCREMENT," +
                    "  `right` VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                    "  UNIQUE INDEX `right_UNIQUE` (`right` ASC));";
            case ROLE_RIGHT -> "  CREATE TABLE IF NOT EXISTS role_right (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  role_id INT NOT NULL," +
                    "  right_id INT NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  INDEX role_id_idx (role_id ASC)," +
                    "  INDEX right_id_idx (right_id ASC)," +
                    "  CONSTRAINT role_id" +
                    "    FOREIGN KEY (role_id)" +
                    "    REFERENCES role (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE," +
                    "  CONSTRAINT right_id" +
                    "    FOREIGN KEY (right_id)" +
                    "    REFERENCES `right` (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE);";
            case USER_ROLE -> "\tCREATE TABLE IF NOT EXISTS user_role (" +
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
            default -> "";
        };
    }

}