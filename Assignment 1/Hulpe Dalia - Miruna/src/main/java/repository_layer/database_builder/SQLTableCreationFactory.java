package repository_layer.database_builder;

import static repository_layer.database_builder.Constants.Tables.*;

public class SQLTableCreationFactory {

  public String getCreateSQLForTable(String table) {
    return switch (table) {
      case USER -> "CREATE TABLE IF NOT EXISTS user (" +
          "  id INT NOT NULL AUTO_INCREMENT," +
          "  username VARCHAR(200) NOT NULL," +
          "  password VARCHAR(64) NOT NULL," +
          "  id_series VARCHAR(3) NOT NULL,"+
          "  id_number INT NOT NULL,"+
          "  pnc VARCHAR(13) NOT NULL,"+
          "  address VARCHAR(200) NOT NULL,"+
          "  PRIMARY KEY (id)," +
          "  UNIQUE INDEX id_UNIQUE (id ASC)," +
          "  UNIQUE INDEX pnc_UNIQUE (pnc ASC)," +
          "  UNIQUE INDEX username_UNIQUE (username ASC));";
      case ROLE -> "  CREATE TABLE IF NOT EXISTS role (" +
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
          "  CONSTRAINT user_fid" +
          "    FOREIGN KEY (user_id)" +
          "    REFERENCES user (id)" +
          "    ON DELETE CASCADE" +
          "    ON UPDATE CASCADE," +
          "  CONSTRAINT role_fkid" +
          "    FOREIGN KEY (role_id)" +
          "    REFERENCES role (id)" +
          "    ON DELETE CASCADE" +
          "    ON UPDATE CASCADE);";
      case ACCOUNT -> "CREATE TABLE IF NOT EXISTS account (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  type VARCHAR(64) NOT NULL," +
              "  amount_of_money FLOAT NOT NULL,"+
              "  date_of_creation DATE NOT NULL,"+
              "  user_id INT NOT NULL,"+
              "  PRIMARY KEY (id)," +
              "  UNIQUE INDEX id_UNIQUE (id ASC),"+
              "  INDEX user_id (user_id ASC)," +
              "  CONSTRAINT user_fid_2" +
              "    FOREIGN KEY (user_id)" +
              "    REFERENCES user (id)" +
              "    ON DELETE CASCADE" +
              "    ON UPDATE CASCADE);";
      case ACTION -> "CREATE TABLE IF NOT EXISTS action (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  type VARCHAR(64) NOT NULL," +
              "  date_of_creation DATE NOT NULL,"+
              "  user_id INT NOT NULL,"+
              "  PRIMARY KEY (id)," +
              "  UNIQUE INDEX id_UNIQUE (id ASC),"+
              "  INDEX user_id (user_id ASC)," +
              "  CONSTRAINT user_fid_1" +
              "    FOREIGN KEY (user_id)" +
              "    REFERENCES user (id)" +
              "    ON DELETE CASCADE" +
              "    ON UPDATE CASCADE);";
      default -> "";
    };
  }
}
