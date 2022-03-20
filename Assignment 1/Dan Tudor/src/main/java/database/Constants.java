package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.*;
import static database.Constants.Roles.*;

public class Constants {

  public static class Schemas {
//    public static final String TEST = "test_library";
//    public static final String PRODUCTION = "library";
//
//    public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};

    public static final String TEST = "test_bank";
    public static final String PRODUCTION = "bank";

    public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
  }

  public static class Tables {
    public static final String BOOK = "book";
    public static final String USER = "user";
    public static final String ROLE = "role";
    public static final String RIGHT = "right";
    public static final String ROLE_RIGHT = "role_right";
    public static final String USER_ROLE = "user_role";
    public static final String CLIENT = "client";

    public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE, CLIENT};
  }

  public static class Roles {
    //public static final String ADMINISTRATOR = "administrator";
    //public static final String EMPLOYEE = "employee";
    //public static final String CUSTOMER = "customer";

    //public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};//, CUSTOMER};
    public static final String USER = "user";
    public static final String ADMIN = "admin";
    public static final String[] ROLES = new String[]{USER, ADMIN};

  }

  public static class Rights {
    //public static final String CREATE_USER = "create_user";
    //public static final String DELETE_USER = "delete_user";
    //public static final String UPDATE_USER = "update_user";

    public static final String CREATE_BOOK = "create_book";
    public static final String DELETE_BOOK = "delete_book";
    public static final String UPDATE_BOOK = "update_book";

    public static final String SELL_BOOK = "sell_book";
    public static final String BUY_BOOK = "buy_book";
    public static final String RETURN_BOOK = "return_book";

    //public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER, CREATE_BOOK,
        //DELETE_BOOK, UPDATE_BOOK, SELL_BOOK, BUY_BOOK, RETURN_BOOK};

    public static final String ADD_CLIENT_INFO = "add_client_info";
    public static final String UPDATE_CLIENT_INFO = "update_client_info";
    public static final String VIEW_CLIENT_INFO = "view_client_info";

    public static final String CREATE_CLIENT = "create_client";
    public static final String UPDATE_CLIENT = "update_client";
    public static final String DELETE_CLIENT = "delete_client";
    public static final String VIEW_CLIENT = "view_client";

    public static final String CREATE_USER = "create_user";
    public static final String READ_USER = "read_user";
    public static final String UPDATE_USER = "update_user";
    public static final String DELETE_USER = "delete_user";

    public static final String[] RIGHTS = new String[]{ADD_CLIENT_INFO,UPDATE_CLIENT_INFO,VIEW_CLIENT_INFO,
    CREATE_CLIENT,UPDATE_CLIENT,DELETE_CLIENT,VIEW_CLIENT,CREATE_USER,READ_USER,UPDATE_USER,DELETE_USER};
  }

  public static Map<String, List<String>> getRolesRights() {
    Map<String, List<String>> ROLES_RIGHTS = new HashMap<>();
    for (String role : ROLES) {
      ROLES_RIGHTS.put(role, new ArrayList<>());
    }
    //ROLES_RIGHTS.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS));
    //ROLES_RIGHTS.get(EMPLOYEE).addAll(Arrays.asList(CREATE_BOOK, DELETE_BOOK, UPDATE_BOOK, SELL_BOOK));
    //ROLES_RIGHTS.get(CUSTOMER).addAll(Arrays.asList(SELL_BOOK, BUY_BOOK, RETURN_BOOK));

    ROLES_RIGHTS.get(USER).addAll(Arrays.asList(ADD_CLIENT_INFO,UPDATE_CLIENT_INFO,VIEW_CLIENT_INFO,CREATE_CLIENT,UPDATE_CLIENT,DELETE_CLIENT,VIEW_CLIENT));
    ROLES_RIGHTS.get(ADMIN).addAll(Arrays.asList(CREATE_USER,READ_USER,UPDATE_USER,DELETE_USER));

    return ROLES_RIGHTS;
  }

}
