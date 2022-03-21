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
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS));

        rolesRights.get(EMPLOYEE).addAll(Arrays.asList(CREATE_USER, DELETE_USER, UPDATE_USER,CREATE_CLIENT_ACCOUNT,UPDATE_CLIENT_ACCOUNT,DELETE_CLIENT_ACCOUNT));



        return rolesRights;
    }

    public static class Schemas{
        public static final String TEST = "test_bank";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{PRODUCTION, TEST};
    }

    public static class Tables {
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String CLIENTS = "clients";
        public static final String CLIENT_ACCOUNTS = "client_accounts";
        public static final String EMPLOYEE_ACTIONS = "employee_actions";


        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE,CLIENTS,CLIENT_ACCOUNTS, EMPLOYEE_ACTIONS};
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
        public static final String CREATE_CLIENT_ACCOUNT = "create_client_account";
        public static final String DELETE_CLIENT_ACCOUNT = "delete_client_account";
        public static final String UPDATE_CLIENT_ACCOUNT = "update_client_account";
        public static final String TRANSACTION_ACCOUNTS = "transaction_accounts";

        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER,CREATE_CLIENT,DELETE_CLIENT,UPDATE_CLIENT,CREATE_CLIENT_ACCOUNT,DELETE_CLIENT_ACCOUNT,UPDATE_CLIENT_ACCOUNT,TRANSACTION_ACCOUNTS};
    }
}
