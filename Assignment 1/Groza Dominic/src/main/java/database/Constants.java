package database;

public class Constants {


    public static class Schemas {
        public static final String TEST = "test_front-desk";
        public static final String PRODUCTION = "front-desk";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String CLIENT = "client";
        public static final String ACCOUNT = "account";
        public static final String USER_ROLE = "user_role";
        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE , USER_ROLE,CLIENT,ACCOUNT};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "admin";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

}