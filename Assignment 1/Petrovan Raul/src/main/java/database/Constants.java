package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static database.Constants.Rights.CREATE_ACCOUNT;
import static database.Constants.Rights.CREATE_USER;
import static database.Constants.Rights.DELETE_ACCOUNT;
import static database.Constants.Rights.DELETE_USER;
import static database.Constants.Rights.PROCESS_BILL;
import static database.Constants.Rights.READ_ACCOUNT;
import static database.Constants.Rights.READ_USER;
import static database.Constants.Rights.RIGHTS;
import static database.Constants.Rights.TRANSFER_MONEY;
import static database.Constants.Rights.UPDATE_ACCOUNT;
import static database.Constants.Rights.UPDATE_USER;
import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.CUSTOMER;
import static database.Constants.Roles.EMPLOYEE;
import static database.Constants.Roles.ROLES;

public class Constants {

  public static class Schemas {
//    public static final String TEST = "test_library";
    public static final String PRODUCTION = "SdBankApp";

    public static final String[] SCHEMAS = new String[]{PRODUCTION};
  }

  public static class Tables {
    public static final String ACCOUNT = "account";
    public static final String USER = "user";
    public static final String CLIENT = "client";
    public static final String ROLE = "role";
    public static final String RIGHT = "right";
    public static final String ROLE_RIGHT = "role_right";
    public static final String USER_ROLE = "user_role";


    public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE,
        ACCOUNT, CLIENT};
  }

  public static class Roles {
    public static final String ADMINISTRATOR = "administrator";
    public static final String EMPLOYEE = "employee";
    public static final String CUSTOMER = "customer";

    public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE, CUSTOMER};
  }

  public static class Rights {
    public static final String CREATE_USER = "create_user";
    public static final String DELETE_USER = "delete_user";
    public static final String UPDATE_USER = "update_user";
    public static final String READ_USER = "read_user";

    public static final String CREATE_ACCOUNT = "create_book";
    public static final String DELETE_ACCOUNT = "delete_book";
    public static final String UPDATE_ACCOUNT = "update_book";
    public static final String READ_ACCOUNT = "read_account";


    public static final String TRANSFER_MONEY = "transfer_money";
    public static final String PROCESS_BILL = "process_bill";

    public static final String CREATE_EMPLOYEE = "create_employee";
    public static final String READ_EMPLOYEE = "read_employee";
    public static final String UPDATE_EMPLOYEE = "update_employee";
    public static final String DELETE_EMPLOYEE = "delete_employee";

    public static final String GENERATE_REPORT = "generate_report";


    public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER,
            READ_USER, CREATE_ACCOUNT, DELETE_ACCOUNT, UPDATE_ACCOUNT, READ_ACCOUNT,
            TRANSFER_MONEY, PROCESS_BILL, CREATE_EMPLOYEE, READ_EMPLOYEE, UPDATE_EMPLOYEE,
            DELETE_EMPLOYEE, GENERATE_REPORT};
  }

  public static Map<String, List<String>> getRolesRights() {
    Map<String, List<String>> ROLES_RIGHTS = new HashMap<>();
    for (String role : ROLES) {
      ROLES_RIGHTS.put(role, new ArrayList<>());
    }
    ROLES_RIGHTS.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS));

    ROLES_RIGHTS.get(EMPLOYEE).addAll(Arrays.asList(CREATE_USER, DELETE_USER, UPDATE_USER,
            READ_USER, CREATE_ACCOUNT, DELETE_ACCOUNT, UPDATE_ACCOUNT, READ_ACCOUNT, TRANSFER_MONEY,
            PROCESS_BILL));

    ROLES_RIGHTS.get(CUSTOMER).addAll(List.of());

    return ROLES_RIGHTS;
  }

}
