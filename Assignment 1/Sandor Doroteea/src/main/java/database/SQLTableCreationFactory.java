package database;

import static database.Constants.Tables.RIGHT;
import static database.Constants.Tables.ROLE;
import static database.Constants.Tables.ROLE_RIGHT;
import static database.Constants.Tables.USER;
import static database.Constants.Tables.USER_ROLE;

import static database.Constants.Rights. VIEW_CLIENT;
import static database.Constants.Rights.RIGHTS;
import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;
import static database.Constants.Roles.ROLES;
import static database.Constants.Rights.CREATE_USER;
import static database.Constants.Rights.DELETE_USER;
import static database.Constants.Rights.UPDATE_USER;
import static database.Constants.Rights.GENERATE_REPORT;
import static database.Constants.Rights.VIEW_USER;
import static database.Constants.Rights.CREATE_CLIENT;
import static database.Constants.Rights.UPDATE_CLIENT;
import static database.Constants.Rights.CREATE_ACCOUNT;
import static database.Constants.Rights.DELETE_ACCOUNT;
import static database.Constants.Rights.UPDATE_ACCOUNT;
import static database.Constants.Rights.VIEW_ACCOUNT;
import static database.Constants.Rights.TRANSFER;

import static database.Constants.Tables.ACTIVITY_USER;
import static database.Constants.Tables.RIGHT;
import static database.Constants.Tables.ROLE;
import static database.Constants.Tables.ACCOUNT;
import static database.Constants.Tables.ROLE_RIGHT;
import static database.Constants.Tables.USER;
import static database.Constants.Tables.USER_ROLE;
import static database.Constants.Tables.CLIENT;

public class SQLTableCreationFactory {

    public String getCreateSQLForTable(String table) {
        switch (table) {
            case ACCOUNT: return "CREATE TABLE IF NOT EXISTS account (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  balance INT NOT NULL," +
                    "  type VARCHAR(100) NOT NULL," +
                    "  dateOfCreation datetime DEFAULT NULL," +
                    "  client_id INT NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  INDEX client_id_idx (client_id ASC)," +
                    "  CONSTRAINT client_id" +
                    "    FOREIGN KEY (client_id)" +
                    "    REFERENCES client (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE," ;
            case CLIENT: return "CREATE TABLE IF NOT EXISTS client (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  name varchar(100) NOT NULL," +
                    "  idCardNumber varchar(100) NOT NULL," +
                    "  cnp varchar(100) NOT NULL," +
                    "  address varchar(100) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
            case ACTIVITY_USER: return "CREATE TABLE IF NOT EXISTS activity (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  user_id INT NOT NULL," +
                    "  activity VARCHAR(100) NOT NULL," +
                    "  period datetime DEFAULT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  CONSTRAINT user_fkid" +
                    "    FOREIGN KEY (user_id)" +
                    "    REFERENCES user (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE)";
            case USER :
                return  "CREATE TABLE IF NOT EXISTS user (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  username VARCHAR(200) NOT NULL," +
                    "  password VARCHAR(64) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  UNIQUE INDEX username_UNIQUE (username ASC));";
            case ROLE :
                return "  CREATE TABLE IF NOT EXISTS role (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  role VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  UNIQUE INDEX role_UNIQUE (role ASC));";
            case RIGHT :
                return "  CREATE TABLE IF NOT EXISTS `right` (" +
                    "  `id` INT NOT NULL AUTO_INCREMENT," +
                    "  `right` VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                    "  UNIQUE INDEX `right_UNIQUE` (`right` ASC));";
            case ROLE_RIGHT :
                return "  CREATE TABLE IF NOT EXISTS role_right (" +
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
            case USER_ROLE :
                return "\tCREATE TABLE IF NOT EXISTS user_role (" +
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
            default :
                return "";
        }
    }

}