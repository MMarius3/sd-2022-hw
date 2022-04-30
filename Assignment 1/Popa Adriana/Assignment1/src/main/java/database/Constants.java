package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.*;
import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;
import static database.Constants.Roles.ROLES;

public class Constants {

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> rolesRights = new HashMap<>();
        for (String role : ROLES) {
            rolesRights.put(role, new ArrayList<>());
        }
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(ADMINISTRATOR_RIGHTS));

        rolesRights.get(EMPLOYEE).addAll(Arrays.asList(EMPLOYEE_RIGHTS));

        return rolesRights;
    }

    public static class Schemas {
        public static final String TEST = "test_bank";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String CLIENT = "client";
        public static final String ACCOUNT = "account";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String REPORT = "report";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, REPORT, USER_ROLE, CLIENT, ACCOUNT};
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
        public static final String VIEW_USER = "view_user";

        public static final String GENERATE_REPORTS = "generate-reports";

        public static final String ADD_CLIENT = "add_client";
        public static final String VIEW_CLIENT = "view_client";
        public static final String UPDATE_CLIENT = "update_client";

        public static final String CREATE_ACCOUNT = "create_account";
        public static final String UPDATE_ACCOUNT = "update_account";
        public static final String DELETE_ACCOUNT = "delete_account";
        public static final String VIEW_ACCOUNT = "view_account";

        public static final String TRANSFER_MONEY = "transfer_money";
        public static final String PROCESS_BILLS = "process_bills";

        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER, VIEW_USER, GENERATE_REPORTS, ADD_CLIENT, UPDATE_CLIENT, VIEW_CLIENT, CREATE_ACCOUNT, UPDATE_ACCOUNT, DELETE_ACCOUNT, VIEW_ACCOUNT, TRANSFER_MONEY, PROCESS_BILLS};
        public static final String[] ADMINISTRATOR_RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER, VIEW_USER, GENERATE_REPORTS};
        public static final String[] EMPLOYEE_RIGHTS = new String[]{ADD_CLIENT, UPDATE_CLIENT, VIEW_CLIENT, CREATE_ACCOUNT, UPDATE_ACCOUNT, DELETE_ACCOUNT, VIEW_ACCOUNT, TRANSFER_MONEY, PROCESS_BILLS};
    }

}