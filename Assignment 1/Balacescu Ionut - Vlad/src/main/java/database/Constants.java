package database;

//import static database.Constants.Rights.*;
//import static database.Constants.Roles.*;

public class Constants {

  public static class Schemas {
    public static final String TEST = "test_assign1";
    public static final String PRODUCTION = "assign1";

    public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
  }

  public static class Tables {
    public static final String USER = "user";
    public static final String ACCOUNT = "account";
    public static final String ROLE = "role";
    public static final String CLIENT = "client";
    public static final String CLIENT_ACCOUNT = "client_account";
    public static final String USER_ROLE = "user_role";
    public static final String ACTION = "action";

    public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ACCOUNT,ROLE,CLIENT,CLIENT_ACCOUNT,USER_ROLE,ACTION};
  }

  public static class Roles {
    public static final String ADMINISTRATOR = "administrator";
    public static final String EMPLOYEE = "employee";
    public static final String CUSTOMER = "customer";

    public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE, CUSTOMER};
  }
//
//  public static class Rights {
//    public static final String CREATE_USER = "create_user";
//    public static final String DELETE_USER = "delete_user";
//    public static final String UPDATE_USER = "update_user";
//
//    public static final String CREATE_BOOK = "create_book";
//    public static final String DELETE_BOOK = "delete_book";
//    public static final String UPDATE_BOOK = "update_book";
//
//    public static final String SELL_BOOK = "sell_book";
//    public static final String BUY_BOOK = "buy_book";
//    public static final String RETURN_BOOK = "return_book";
//
//    public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER, CREATE_BOOK,
//        DELETE_BOOK, UPDATE_BOOK, SELL_BOOK, BUY_BOOK, RETURN_BOOK};
//  }
//
//  public static Map<String, List<String>> getRolesRights() {
//    Map<String, List<String>> ROLES_RIGHTS = new HashMap<>();
//    for (String role : ROLES) {
//      ROLES_RIGHTS.put(role, new ArrayList<>());
//    }
//    ROLES_RIGHTS.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS));
//
//    ROLES_RIGHTS.get(EMPLOYEE).addAll(Arrays.asList(CREATE_BOOK, DELETE_BOOK, UPDATE_BOOK, SELL_BOOK));
//
//    ROLES_RIGHTS.get(CUSTOMER).addAll(Arrays.asList(SELL_BOOK, BUY_BOOK, RETURN_BOOK));
//
//    return ROLES_RIGHTS;
//  }

}
