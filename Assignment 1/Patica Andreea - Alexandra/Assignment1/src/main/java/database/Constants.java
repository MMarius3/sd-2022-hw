package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.CREATE_USER;
import static database.Constants.Rights.DELETE_USER;
import static database.Constants.Rights.RIGHTS;
import static database.Constants.Rights.UPDATE_USER;
import static database.Constants.Rights.CREATE_CLIENT;
import static database.Constants.Rights.UPDATE_CLIENT;
import static database.Constants.Rights.CREATE_ACCOUNT;
import static database.Constants.Rights.UPDATE_ACCOUNT;
import static database.Constants.Rights.DELETE_ACCOUNT;
import static database.Constants.Rights.GENERATE_REPORT;
import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.USER;
import static database.Constants.Roles.ROLES;

public class Constants {

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> rolesRights = new HashMap<>();
        for (String role : ROLES) {
            rolesRights.put(role, new ArrayList<>());
        }
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(CREATE_USER, DELETE_USER, UPDATE_USER, GENERATE_REPORT));

        rolesRights.get(USER).addAll(Arrays.asList(CREATE_CLIENT, UPDATE_CLIENT, CREATE_ACCOUNT, UPDATE_ACCOUNT, DELETE_ACCOUNT));


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
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE,
                ACCOUNT_TYPE, CLIENT, ACCOUNT};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String USER = "user";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, USER};
    }

    public static class AccountTypes{
        public static final String CHECKING_ACCOUNT = "checking_account";
        public static final String SAVINGS_ACCOUNT = "savings_account";
        public static final String MONEY_MARKET_DEPOSIT_ACCOUNT = "money_making_deposit_account";
        public static final String CERTIFICATE_OF_DEPOSIT = "certificate_of_deposit";

        public static final String[] ACCOUNT_TYPES = new String[]{CHECKING_ACCOUNT, SAVINGS_ACCOUNT, MONEY_MARKET_DEPOSIT_ACCOUNT,
                CERTIFICATE_OF_DEPOSIT};
    }

    public static class Rights {
        public static final String CREATE_USER = "create_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";
        public static final String GENERATE_REPORT = "generate_report";

        public static final String CREATE_CLIENT = "create_client";
        public static final String UPDATE_CLIENT = "update_client";

        public static final String CREATE_ACCOUNT = "create_account";
        public static final String UPDATE_ACCOUNT = "update_account";
        public static final String DELETE_ACCOUNT = "delete_account";

        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER, GENERATE_REPORT,
                CREATE_CLIENT, UPDATE_CLIENT, CREATE_ACCOUNT, UPDATE_ACCOUNT, DELETE_ACCOUNT};
    }

}
