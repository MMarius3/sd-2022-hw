package database;

import java.util.*;

import static database.Constants.Roles.ROLES;
import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

import static database.Constants.Rights.RIGHTS;
import static database.Constants.Rights.ADMINISTRATOR_RIGHTS;
import static database.Constants.Rights.EMPLOYEE_RIGHTS;

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
        public static final String CLIENT_ACCOUNT = "client_account";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String CLIENT_CLIENT_ACCOUNT = "client_client_account";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE,
                CLIENT, CLIENT_ACCOUNT, CLIENT_CLIENT_ACCOUNT};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {

        //regular user (employee) rights
        public static final String CREATE_CLIENT = "create_client";
        public static final String UPDATE_CLIENT = "update_client";

        public static final String CREATE_CLIENT_ACCOUNT = "create_client_account";
        public static final String DELETE_CLIENT_ACCOUNT = "delete_client_account";
        public static final String UPDATE_CLIENT_ACCOUNT = "update_client_account";

        public static final String TRANSFER_MONEY = "transfer_money";
        public static final String PROCESS_UTILITY_BILL = "process_utility_bill";

        //administrator rights
        public static final String CREATE_EMPLOYEE = "create_employee";
        public static final String DELETE_EMPLOYEE = "delete_employee";
        public static final String UPDATE_EMPLOYEE = "update_employee";

        public static final String GENERATE_REPORT = "generate_report";

        //union
        public static final String[] ADMINISTRATOR_RIGHTS = new String[]{
                CREATE_EMPLOYEE, DELETE_EMPLOYEE, UPDATE_EMPLOYEE,
                GENERATE_REPORT};

        public static final String[] EMPLOYEE_RIGHTS = new String[]{
                CREATE_CLIENT, UPDATE_CLIENT,
                CREATE_CLIENT_ACCOUNT, DELETE_CLIENT_ACCOUNT, UPDATE_CLIENT_ACCOUNT,
                TRANSFER_MONEY, PROCESS_UTILITY_BILL};

        public static final String[] RIGHTS = new String[]{CREATE_CLIENT, UPDATE_CLIENT,
                CREATE_CLIENT_ACCOUNT, DELETE_CLIENT_ACCOUNT, UPDATE_CLIENT_ACCOUNT,
                TRANSFER_MONEY, PROCESS_UTILITY_BILL,
                CREATE_EMPLOYEE, DELETE_EMPLOYEE, UPDATE_EMPLOYEE,
                GENERATE_REPORT};
    }


}
