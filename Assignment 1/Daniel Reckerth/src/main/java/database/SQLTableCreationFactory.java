package database;

import database.enums.TableTypeSQL;

public class SQLTableCreationFactory {

  public String getCreateSQLForTable(TableTypeSQL tableTypeSQL) {
    return switch(tableTypeSQL) {
      case USER -> "CREATE TABLE IF NOT EXISTS user (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  username VARCHAR(200) NOT NULL," +
              "  password VARCHAR(64) NOT NULL," +
              "  PRIMARY KEY (id)," +
              "  UNIQUE INDEX id_UNIQUE (id ASC)," +
              "  UNIQUE INDEX username_UNIQUE (username ASC));";
      case CLIENT -> "CREATE TABLE IF NOT EXISTS client (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  name VARCHAR(200) NOT NULL," +
              "  card_number VARCHAR(16) NOT NULL, " +
              "  ssn VARCHAR(13) NOT NULL," +
              "  address VARCHAR(200) NOT NULL," +
              "  PRIMARY KEY (id)," +
              "  UNIQUE INDEX id_UNIQUE(id ASC)," +
              "  UNIQUE INDEX card_number_UNIQUE(card_number ASC)," +
              "  UNIQUE INDEX ssn_UNIQUE(ssn ASC));";
      case ACCOUNT -> "CREATE TABLE IF NOT EXISTS account (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  acc_type VARCHAR(50) DEFAULT NULL," +
              "  balance DOUBLE DEFAULT 0.0," +
              "  creation_date datetime DEFAULT NULL," +
              "  client_id INT NOT NULL," +
              "  PRIMARY KEY(id)," +
              "  UNIQUE INDEX id_UNIQUE(id ASC)," +
              "  INDEX client_id_idx(client_id ASC)," +
              "  CONSTRAINT client_fkid" +
              "    FOREIGN KEY (client_id)" +
              "    REFERENCES client (id)" +
              "    ON DELETE CASCADE" +
              "    ON UPDATE CASCADE" +
              "  );";
      case ROLE -> "CREATE TABLE IF NOT EXISTS role (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  role VARCHAR(100) NOT NULL," +
              "  PRIMARY KEY (id)," +
              "  UNIQUE INDEX id_UNIQUE (id ASC)," +
              "  UNIQUE INDEX role_UNIQUE (role ASC));";
      case RIGHT -> "CREATE TABLE IF NOT EXISTS `right` (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  name VARCHAR(100) NOT NULL," +
              "  PRIMARY KEY (id)," +
              "  UNIQUE INDEX id_UNIQUE (id ASC)," +
              "  UNIQUE INDEX name_UNIQUE (name ASC));";
      case ACTIVITY -> "CREATE TABLE IF NOT EXISTS activity (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  performed_at timestamp DEFAULT NULL," +
              "  employee_id INT NOT NULL," +
              "  activity_id INT NOT NULL," +
              "  PRIMARY KEY(id)," +
              "  UNIQUE INDEX id_UNIQUE(id ASC)," +
              "  INDEX employee_id_idx(employee_id ASC)," +
              "  INDEX activity_id_idx(activity_id ASC)," +
              "  CONSTRAINT employee_fkid" +
              "    FOREIGN KEY (employee_id)" +
              "    REFERENCES user (id)" +
              "    ON DELETE CASCADE" +
              "    ON UPDATE CASCADE," +
              "  CONSTRAINT activity_fkid" +
              "    FOREIGN KEY (activity_id)" +
              "    REFERENCES `right` (id)" +
              "    ON DELETE CASCADE" +
              "    ON UPDATE CASCADE" +
              "  );";
      case ROLE_RIGHT -> "CREATE TABLE IF NOT EXISTS role_right (" +
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
    };
  }
}
