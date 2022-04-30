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
            case ACCOUNT -> "CREATE TABLE IF NOT EXISTS account (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  id_Number int(11) NOT NULL," +
                    "  type_account varchar(500) NOT NULL," +
                    "  amount_money int(11),"+
                    "  date_of_creation varchar(500) NOT NULL,"+
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC));";
            case ROLE -> "  CREATE TABLE IF NOT EXISTS role (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  role VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  UNIQUE INDEX role_UNIQUE (role ASC));";
            case CLIENT -> "CREATE TABLE IF NOT EXISTS client (" +
                    "  id int(11) NOT NULL AUTO_INCREMENT," +
                    "  name varchar(500) NOT NULL," +
                    "  address varchar(500)," +
                    "  pnc varchar(500) NOT NULL," +
                    "  id_cardNr varchar(500) NOT NULL,"+
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC));";
            case CLIENT_ACCOUNT -> "CREATE TABLE IF NOT EXISTS client_account (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  client_id INT NOT NULL," +
                    "  account_id INT NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  INDEX client_id_idx (client_id ASC)," +
                    "  INDEX account_id_idx (account_id ASC)," +
                    "  CONSTRAINT client_fkid" +
                    "    FOREIGN KEY (client_id)" +
                    "    REFERENCES client (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE," +
                    "  CONSTRAINT account_fkid" +
                    "    FOREIGN KEY (account_id)" +
                    "    REFERENCES account (id)" +
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
            case ACTION -> "CREATE TABLE IF NOT EXISTS actions (" +
                    "  id int(11) NOT NULL AUTO_INCREMENT," +
                    "  username varchar(500) NOT NULL," +
                    "  name varchar(500)," +
                    "  date varchar(500) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC));";
            default -> "";
        };
    }
}
