package database;

import java.util.*;

import static database.Constants.Rights.ADMINISTRATOR_RIGHTS;
import static database.Constants.Rights.EMPLOYEE_RIGHTS;
import static database.Constants.Roles.*;

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
        public static final String ACTION = "action";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE, CLIENT, ACCOUNT};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class AccountTypes{
        public static final String DEBIT = "debit";
        public static final String CREDIT = "credit";
    }

    public static class Rights {

        // EMPLOYEE RIGHTS

        public static final String CREATE_CLIENT = "create_client";
        public static final String UPDATE_CLIENT = "update_client";
        public static final String VIEW_CLIENT = "view_client";
        public static final String CREATE_ACCOUNT = "create_account";
        public static final String UPDATE_ACCOUNT = "update_account";
        public static final String DELETE_ACCOUNT = "delete_account";
        public static final String VIEW_ACCOUNT = "view_account";
        public static final String TRANSFER = "transfer";
        public static final String BILLS = "bills";

        public static final String[] EMPLOYEE_RIGHTS = new String[]{CREATE_CLIENT, UPDATE_CLIENT, VIEW_CLIENT, CREATE_ACCOUNT, UPDATE_ACCOUNT, DELETE_ACCOUNT, VIEW_ACCOUNT, TRANSFER, BILLS};

        // ADMINISTRATOR RIGHTS

        public static final String CREATE_EMPLOYEE = "create_employee";
        public static final String VIEW_EMPLOYEE = "view_employee";
        public static final String UPDATE_EMPLOYEE = "update_employee";
        public static final String DELETE_EMPLOYEE = "delete_employee";
        public static final String GENERATE_REPORTS = "generate_reports";

        public static final String[] ADMINISTRATOR_RIGHTS = new String[]{CREATE_EMPLOYEE, VIEW_EMPLOYEE, UPDATE_EMPLOYEE, DELETE_EMPLOYEE, GENERATE_REPORTS};
    }

}
