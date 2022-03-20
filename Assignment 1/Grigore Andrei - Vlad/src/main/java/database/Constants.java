package database;

import model.Right;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.*;
import static database.Constants.Roles.*;


public class Constants {

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> rolesRights = new HashMap<>();
        for (String role : ROLES) {
            rolesRights.put(role, new ArrayList<>());
        }
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS));

        rolesRights.get(EMPLOYEE).addAll(Arrays.asList(CREATE_USER, DELETE_USER, UPDATE_USER, VIEW_USER, CREATE_ACCOUNT, UPDATE_ACCOUNT,DELETE_ACCOUNT,VIEW_ACCOUNT,TRANSFER_MONEY,PROCESS_BILLS));

        return rolesRights;
    }
    public static class Schemas {
        public static final String TEST = "test";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST};
    }

    public static class Tables {
        public static final String CLIENT ="client";
        public static final String ACCOUNT = "account";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String ACTION = "action";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{CLIENT,ACCOUNT, USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE,ACTION};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {
        public static final String CREATE_CLIENT = "create_client";
        public static final String DELETE_CLIENT = "delete_client";
        public static final String UPDATE_CLIENT = "update_client";
        public static final String VIEW_CLIENT = "view_client";
        public static final String CREATE_USER = "create_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";
        public static final String VIEW_USER = "view_user";
        public static final String CREATE_ACCOUNT = "create_account";
        public static final String UPDATE_ACCOUNT = "update_account";
        public static final String DELETE_ACCOUNT = "delete_account";
        public static final String VIEW_ACCOUNT = "view_account";
        public static final String TRANSFER_MONEY = "transfer_money";
        public static final String PROCESS_BILLS = "process_bills";


        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER,VIEW_USER,
                CREATE_ACCOUNT,UPDATE_ACCOUNT,DELETE_ACCOUNT,VIEW_ACCOUNT,TRANSFER_MONEY,PROCESS_BILLS,
                CREATE_CLIENT,UPDATE_CLIENT,DELETE_CLIENT,VIEW_CLIENT};

        public static final Right rightEmployee1 = new Right(CREATE_CLIENT);
        public static final Right rightEmployee2 = new Right(UPDATE_CLIENT);
        public static final Right rightEmployee3 = new Right(DELETE_CLIENT);
        public static final Right rightEmployee4 = new Right(CREATE_ACCOUNT);
        public static final Right rightEmployee5 = new Right(UPDATE_ACCOUNT);
        public static final Right rightEmployee6 = new Right(DELETE_ACCOUNT);
        public static final Right rightEmployee7 = new Right(TRANSFER_MONEY);
        public static final Right rightEmployee8 = new Right(PROCESS_BILLS);
        public static final Right rightEmployee9 = new Right(VIEW_ACCOUNT);
        public static final Right rightEmployee10 = new Right(VIEW_CLIENT);

        public static final Right[] employeeRights = new Right[]{rightEmployee1,rightEmployee2,
                rightEmployee3,rightEmployee4,rightEmployee5,rightEmployee6,
                rightEmployee7,rightEmployee8,rightEmployee9,rightEmployee10};


    }


}
