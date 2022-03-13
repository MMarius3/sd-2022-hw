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
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(ADMIN_RIGHTS));

        rolesRights.get(EMPLOYEE).addAll(Arrays.asList(EMP_RIGHTS));

        return rolesRights;
    }

    public static class Schemas {
        public static final String TEST = "test_bank";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String USER = "USER";
        public static final String CLIENT = "CLIENT";
        public static final String ROLE = "ROLE";
        public static final String RIGHT = "RIGHT";
        public static final String ROLE_RIGHT = "ROLE_RIGHT";
        public static final String USER_ROLE = "USER_ROLE";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, CLIENT, ROLE,
                RIGHT, ROLE_RIGHT, USER_ROLE};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "ADMINISTRATOR";
        public static final String EMPLOYEE = "EMPLOYEE";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {
        public static final String CREATE_CLIENT_INF = "create_client_inf";
        public static final String UPDATE_CLIENT_INF = "update_client_inf";
        public static final String VIEW_CLIENT_INF = "view_client_inf";

        public static final String CREATE_CLIENT_ACC = "create_client_acc";
        public static final String UPDATE_CLIENT_ACC = "update_client_acc";
        public static final String DELETE_CLIENT_ACC = "delete_client_acc";
        public static final String VIEW_CLIENT_ACC = "view_client_acc";

        public static final String TRANSFER_MONEY = "transfer_money";
        public static final String PROCESS_BILLS = "process_bills";

        public static final String CREATE_EMP = "create_emp";
        public static final String VIEW_EMP = "view_emp";
        public static final String UPDATE_EMP = "update_emp";
        public static final String DELETE_EMP = "delete_emp";
        public static final String GENERATE_REPORTS = "generate_reports";


        public static final String[] EMP_RIGHTS = new String[]{CREATE_CLIENT_INF, UPDATE_CLIENT_INF, VIEW_CLIENT_INF,
                CREATE_CLIENT_ACC, UPDATE_CLIENT_ACC, DELETE_CLIENT_ACC, VIEW_CLIENT_ACC, TRANSFER_MONEY, PROCESS_BILLS};

        public static final String[] ADMIN_RIGHTS = new String[]{CREATE_EMP, VIEW_EMP, UPDATE_EMP, DELETE_EMP, GENERATE_REPORTS};
    }

}