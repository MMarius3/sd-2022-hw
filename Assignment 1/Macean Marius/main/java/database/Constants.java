package database;

import java.util.*;

import static database.Constants.Rights.*;
import static database.Constants.Roles.*;

public class Constants {

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> rolesRights = new HashMap<>();
        for (String role : ROLES) {
            rolesRights.put(role, new ArrayList<>());
        }
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(CREATE_USER, DELETE_USER, UPDATE_USER, GENERATE_REPORTS));

        rolesRights.get(EMPLOYEE).addAll(Arrays.asList(CREATE_CLIENT, UPDATE_CLIENT,
                CREATE_ACCOUNT, DELETE_ACCOUNT, UPDATE_ACCOUNT, EXECUTE_BILLS, EXECUTE_TRANSFERS));

        return rolesRights;
    }

    public static class Schemas {
        public static final String TEST = "test_library";
        public static final String PRODUCTION = "library";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String CLIENT = "client";
        public static final String ACCOUNT = "account";
        public static final String CLIENT_ACCOUNT = "client_account";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE,
                CLIENT, ACCOUNT, CLIENT_ACCOUNT};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {
        public static final String CREATE_USER = "create_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";

        public static final String CREATE_CLIENT = "create_client";
        public static final String DELETE_CLIENT = "delete_client";
        public static final String UPDATE_CLIENT = "update_client";

        public static final String CREATE_ACCOUNT = "create_account";
        public static final String DELETE_ACCOUNT = "delete_account";
        public static final String UPDATE_ACCOUNT = "update_account";

        public static final String GENERATE_REPORTS = "generate_reports";
        public static final String EXECUTE_BILLS = "execute_bills";
        public static final String EXECUTE_TRANSFERS = "execute_transfers";

        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER, GENERATE_REPORTS,
                CREATE_CLIENT, DELETE_CLIENT, UPDATE_CLIENT,
                CREATE_ACCOUNT, DELETE_ACCOUNT, UPDATE_ACCOUNT,
                EXECUTE_BILLS, EXECUTE_TRANSFERS};
    }

}