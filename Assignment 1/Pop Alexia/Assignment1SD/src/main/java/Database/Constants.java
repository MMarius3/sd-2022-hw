package Database;

import java.util.*;

import static Database.Constants.Rights.*;
import static Database.Constants.Roles.ADMINISTRATOR;
import static Database.Constants.Roles.EMPLOYEE;
import static Database.Constants.Roles.ROLES;


public class Constants {

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> rolesRights = new HashMap<>();
        for (String role : ROLES) {
            rolesRights.put(role, new ArrayList<>());
        }
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(CREATE_USER,UPDATE_USER,DELETE_USER,VIEW_USER,GENERATE_REPORT));

        rolesRights.get(EMPLOYEE).addAll(Arrays.asList(CREATE_CLIENT, VIEW_CLIENT, UPDATE_CLIENT, CREATE_ACCOUNT,UPDATE_ACCOUNT,DELETE_ACCOUNT,VIEW_ACCOUNT,PROCESS_BILLS,TRANSFER_MONEY));

        return rolesRights;
    }

    public static class Schemas {
        public static final String PRODUCTION = "assignment1";
        public static final String TEST = "assignment1test";

        public static final String[] SCHEMAS = new String[]{PRODUCTION,TEST};
    }

    public static class Tables {
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String CLIENT = "client";
        public static final String ACCOUNT = "account";
        public static final String EVENT = "event";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE,ACCOUNT,CLIENT,EVENT};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }
    public static class AccountType{
        public static final String SAVINGS = "savings";
        public static final String INVESTMENT = "investment";
    }

    public static class Rights {
        public static final String CREATE_USER = "create_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";
        public static final String VIEW_USER = "view_user";

        public static final String CREATE_ACCOUNT = "create_account";
        public static final String DELETE_ACCOUNT = "delete_account";
        public static final String UPDATE_ACCOUNT = "update_account";
        public static final String VIEW_ACCOUNT = "view_account";

        public static final String CREATE_CLIENT = "create_client";
        public static final String UPDATE_CLIENT = "update_client";
        public static final String VIEW_CLIENT = "view_client";

        public static final String GENERATE_REPORT = "generate_report";
        public static final String TRANSFER_MONEY = "transfer_money";
        public static final String PROCESS_BILLS = "process_bills";

        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER,VIEW_USER, CREATE_CLIENT,
                VIEW_CLIENT, UPDATE_CLIENT, CREATE_ACCOUNT, UPDATE_ACCOUNT,DELETE_ACCOUNT,VIEW_ACCOUNT,TRANSFER_MONEY,PROCESS_BILLS,GENERATE_REPORT};
    }

}
