package database;


import static database.Constants.Tables.ACCOUNT;
import static database.Constants.Tables.CLIENT;
import static database.Constants.Tables.LOG;
import static database.Constants.Tables.RIGHT;
import static database.Constants.Tables.ROLE;
import static database.Constants.Tables.ROLE_RIGHT;
import static database.Constants.Tables.USER;
import static database.Constants.Tables.USER_ROLE;

public class SQLTableCreationFactory {

  public String getCreateSQLForTable(String table) {
    return switch (table) {

      case USER -> "create table \"user\"\n" +
              "(\n" +
              "    id       serial\n" +
              "        constraint user_pk\n" +
              "            primary key,\n" +
              "    username varchar(255),\n" +
              "    password varchar(64)\n" +
              ");\n" +
              "\n" +
              "alter table \"user\"\n" +
              "    owner to sd_user;\n" +
              "\n" +
              "create unique index user_id_uindex\n" +
              "    on \"user\" (id);\n" +
              "\n" +
              "create unique index user_username_uindex\n" +
              "    on \"user\" (username);";
      case ROLE -> "create table role\n" +
              "(\n" +
              "    id   serial\n" +
              "        constraint role_pk\n" +
              "            primary key,\n" +
              "    role varchar(100) not null\n" +
              ");\n" +
              "\n" +
              "alter table role\n" +
              "    owner to sd_user;\n" +
              "\n" +
              "create unique index role_id_uindex\n" +
              "    on role (id);\n" +
              "\n" +
              "create unique index role_role_uindex\n" +
              "    on role (role);\n";
      case RIGHT -> "create table \"right\"\n" +
              "(\n" +
              "    id      serial\n" +
              "        constraint right_pk\n" +
              "            primary key,\n" +
              "    \"right\" varchar(100) not null\n" +
              ");\n" +
              "\n" +
              "alter table \"right\"\n" +
              "    owner to sd_user;\n" +
              "\n" +
              "create unique index right_id_uindex\n" +
              "    on \"right\" (id);\n" +
              "\n" +
              "create unique index right_right_uindex\n" +
              "    on \"right\" (\"right\");\n";
      case USER_ROLE -> "create table user_role\n" +
              "(\n" +
              "    id      serial\n" +
              "        constraint user_role_pk\n" +
              "            primary key,\n" +
              "    user_id integer not null\n" +
              "        constraint user_role_user_id_fk\n" +
              "            references \"user\",\n" +
              "    role_id integer not null\n" +
              "        constraint user_role_role_id_fk\n" +
              "            references role\n" +
              ");\nalter table user_role\n" +
              "    owner to sd_user;\n" +
              "\n" +
              "create unique index user_role_id_uindex\n" +
              "    on user_role (id);\n";
      case ACCOUNT -> "create table account\n" +
              "(\n" +
              "    id             serial\n" +
              "        constraint account_pk\n" +
              "            primary key,\n" +
              "    user_id        integer\n" +
              "        constraint account_user_id_fk\n" +
              "            references \"user\",\n" +
              "    balance        numeric(12, 2),\n" +
              "    type           varchar(255),\n" +
              "    creation_date  date,\n" +
              "    account_number varchar(255)\n" +
              ");\n" +
              "\n" +
              "alter table account\n" +
              "    owner to sd_user;\n" +
              "\n" +
              "create unique index account_id_uindex\n" +
              "    on account (id);\n";
      case CLIENT -> "create table client\n" +
              "(\n" +
              "    id         serial\n" +
              "        constraint client_pk\n" +
              "            primary key,\n" +
              "    user_id    integer\n" +
              "        constraint client_user_id_fk\n" +
              "            references \"user\",\n" +
              "    name       varchar(255),\n" +
              "    \"idNumber\" varchar(255),\n" +
              "    cnp        varchar(255),\n" +
              "    address    varchar(255)\n" +
              ");\n" +
              "\n" +
              "alter table client\n" +
              "    owner to sd_user;\n" +
              "\n" +
              "create unique index client_id_uindex\n" +
              "    on client (id);\n";
      case ROLE_RIGHT -> "create table role_right\n" +
              "(\n" +
              "    id       serial\n" +
              "        constraint role_right_pk\n" +
              "            primary key,\n" +
              "    role_id  integer not null\n" +
              "        constraint role_right_role_id_fk\n" +
              "            references role,\n" +
              "    right_id integer not null\n" +
              "        constraint role_right_right_id_fk\n" +
              "            references \"right\",\n" +
              "    constraint role_right_pk_2\n" +
              "        unique (role_id, right_id)\n" +
              ");\n" +
              "\n" +
              "alter table role_right\n" +
              "    owner to sd_user;\n" +
              "\n" +
              "create unique index role_right_id_uindex\n" +
              "    on role_right (id);\n";
      case LOG -> "create table log\n" +
              "(\n" +
              "    id            serial\n" +
              "        constraint log_pk\n" +
              "            primary key,\n" +
              "    user_id       integer,\n" +
              "    action        varchar(255),\n" +
              "    creation_date date\n" +
              ");\n" +
              "\n" +
              "alter table log\n" +
              "    owner to sd_user;\n" +
              "\n" +
              "create unique index log_id_uindex\n" +
              "    on log (id);\n";
      default -> "";
    };
  }
}
