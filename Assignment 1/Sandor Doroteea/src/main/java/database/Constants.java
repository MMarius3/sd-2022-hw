package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static database.Constants.Rights.BUY_BOOK;
//import static database.Constants.Rights.CREATE_BOOK;
//import static database.Constants.Rights.DELETE_BOOK;
//import static database.Constants.Rights.RETURN_BOOK;
//import static database.Constants.Rights.SELL_BOOK;
//import static database.Constants.Rights.UPDATE_BOOK;
import static database.Constants.Rights.*;
import static database.Constants.Roles.ADMINISTRATOR;
//import static database.Constants.Roles.CUSTOMER;
import static database.Constants.Roles.EMPLOYEE;
import static database.Constants.Roles.ROLES;

import static database.Constants.Rights. VIEW_CLIENT;
import static database.Constants.Rights.RIGHTS;
import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;
import static database.Constants.Roles.ROLES;
import static database.Constants.Rights.CREATE_USER;
import static database.Constants.Rights.DELETE_USER;
import static database.Constants.Rights.UPDATE_USER;
import static database.Constants.Rights.GENERATE_REPORT;
import static database.Constants.Rights.VIEW_USER;
import static database.Constants.Rights.CREATE_CLIENT;
import static database.Constants.Rights.UPDATE_CLIENT;
import static database.Constants.Rights.CREATE_ACCOUNT;
import static database.Constants.Rights.DELETE_ACCOUNT;
import static database.Constants.Rights.UPDATE_ACCOUNT;
import static database.Constants.Rights.VIEW_ACCOUNT;
import static database.Constants.Rights.TRANSFER;

public class Constants {

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> rolesRights = new HashMap<>();
        for (String role : ROLES) {
            rolesRights.put(role, new ArrayList<>());
        }
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS));

        rolesRights.get(EMPLOYEE).addAll(Arrays.asList(CREATE_CLIENT, VIEW_CLIENT, UPDATE_CLIENT, CREATE_ACCOUNT, DELETE_ACCOUNT, UPDATE_ACCOUNT, VIEW_ACCOUNT, TRANSFER));


        //rolesRights.get(CUSTOMER).addAll(Arrays.asList(SELL_BOOK, BUY_BOOK, RETURN_BOOK));

        return rolesRights;
    }

    public static class Schemas {
        public static final String TEST = "test_bank2";
        public static final String PRODUCTION = "bank2";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String CLIENT="client";
        public static final String ACCOUNT="account";
        public static final String ACTIVITY_USER="activity_user";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE,
                CLIENT,ACCOUNT,ACTIVITY_USER};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";
        //public static final String CUSTOMER = "customer";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {
        //crud employee
        public static final String CREATE_USER = "create_user";
        public static final String VIEW_USER= "view_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";

        //generate report
        public static final String GENERATE_REPORT="generate_report";

        //add,update,view client information
        public static final String CREATE_CLIENT = "create_client";
        public static final String VIEW_CLIENT="view_client";
        public static final String UPDATE_CLIENT = "update_client";

        //crud client account
        public static final String CREATE_ACCOUNT ="create_account";
        public static final String DELETE_ACCOUNT ="delete_account";
        public static final String UPDATE_ACCOUNT ="update_account";
        public static final String VIEW_ACCOUNT="view_account";

        //transfer money between account
        public static final String TRANSFER="transfer";

        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER,
                GENERATE_REPORT,VIEW_USER,CREATE_CLIENT,UPDATE_CLIENT,CREATE_ACCOUNT,DELETE_ACCOUNT,UPDATE_ACCOUNT,VIEW_ACCOUNT,TRANSFER};
    }

}