package Database;

import static Database.Constants.Tables.*;

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
            case CLIENTS -> "CREATE TABLE IF NOT EXISTS clients (" +
                    " id BIGINT NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(200) NOT NULL," +
                    " identityCardNumber BIGINT NOT NULL," +
                    " personalNumericalCode VARCHAR(100) NOT NULL," +
                    " address VARCHAR(200) NOT NULL," +
                    " PRIMARY KEY (id)," +
                    " UNIQUE INDEX id_UNIQUE (id ASC)," +
                    " UNIQUE INDEX identityCardNumber_UNIQUE (identityCardNumber ASC)," +
                    " UNIQUE INDEX personalNumericalCode_UNIQUE (personalNumericalCode ASC));";
            case CLIENT_ACCOUNTS -> "CREATE TABLE IF NOT EXISTS client_accounts (" +
                    " id BIGINT NOT NULL," +
                    " identificationNumber BIGINT NOT NULL," +
                    " type VARCHAR(200) NOT NULL," +
                    " money INT NOT NULL," +
                    " creationDate DATE NOT NULL," +
                    " FOREIGN KEY (id)" +
                    " REFERENCES clients (id)" +
                    " ON DELETE CASCADE" +
                    " ON UPDATE CASCADE)";
            case EMPLOYEE_ACTIONS -> "CREATE TABLE IF NOT EXISTS employee_actions (" +
                    " id INT NOT NULL," +
                    " action VARCHAR(100) NOT NULL," +
                    " date DATE NOT NULL," +
                    " FOREIGN KEY (id)" +
                    " REFERENCES user (id))";
            default -> "";
        };
    }

}
