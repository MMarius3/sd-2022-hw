package database;

import static database.Constants.Tables.*;

public class SQLTableCreationFactory {

  public String getCreateSQLForTable(String table) {
    return switch (table) {
      case ACCOUNT -> "create table if not exists library.account\n" +
              "(\n" +
              "    id        int auto_increment\n" +
              "        primary key,\n" +
              "    type      varchar(200) null,\n" +
              "    amount    int          null,\n" +
              "    date      date         null,\n" +
              "    client_id int          null,\n" +
              "    constraint account_id_uindex\n" +
              "        unique (id),\n" +
              "    constraint account_client_id_fk\n" +
              "        foreign key (client_id) references library.client (id)\n" +
              ");\n" +
              "\n";
      case CLIENT -> "create table if not exists library.client\n" +
              "(\n" +
              "    id      int auto_increment\n" +
              "        primary key,\n" +
              "    name    varchar(200) null,\n" +
              "    cnp     varchar(20)  null,\n" +
              "    address varchar(200) null,\n" +
              "    constraint client_id_uindex\n" +
              "        unique (id)\n" +
              ");\n" +
              "\n";
     /* case CLIENT_ACCOUNT -> "create table if not exists library.client_account\n" +
              "(\n" +
              "    id         int auto_increment\n" +
              "        primary key,\n" +
              "    client_id  int null,\n" +
              "    account_id int null,\n" +
              "    constraint client_account_id_uindex\n" +
              "        unique (id),\n" +
              "    constraint client_account_account_id_fk\n" +
              "        foreign key (account_id) references library.account (id),\n" +
              "    constraint client_account_client_id_fk\n" +
              "        foreign key (client_id) references library.client (id)\n" +
              ");\n" +
              "\n";*/
      case BOOK -> "CREATE TABLE IF NOT EXISTS book (" +
          "  id int(11) NOT NULL AUTO_INCREMENT," +
          "  author varchar(500) NOT NULL," +
          "  title varchar(500) NOT NULL," +
          "  publishedDate datetime DEFAULT NULL," +
          "  PRIMARY KEY (id)," +
          "  UNIQUE KEY id_UNIQUE (id)" +
          ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
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
      default -> "";
    };
  }
}
