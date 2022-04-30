package repository_layer.database_builder;

import bussiness_layer.builder.AccountBuilder;
import bussiness_layer.builder.UserBuilder;
import bussiness_layer.models.Account;
import bussiness_layer.models.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Constants {

  public static class Schemas {
    public static final String TEST = "test_bank";
    public static final String PRODUCTION = "bank";

    public static final String[] SCHEMAS = new String[]{ TEST, PRODUCTION};
  }

  public static class Tables {
    public static final String USER = "user";
    public static final String ROLE = "role";
    public static final String ACCOUNT = "account";
    public static final String ACTION = "action";
    public static final String USER_ROLE = "user_role";

    public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, ACCOUNT, ACTION, USER_ROLE};
  }

  public static class Roles {
    public static final String ADMINISTRATOR = "administrator";
    public static final String EMPLOYEE = "employee";
    public static final String CUSTOMER = "customer";

    public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE, CUSTOMER};
  }

  private static String encodePassword(String password) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
      StringBuilder hexString = new StringBuilder();

      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }

      return hexString.toString();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public static class Users {
    public static final User ADMIN_USER = new UserBuilder()
            .setUsername("ADMIN_USER@yahoo.com")
            .setPassword(encodePassword("Pass123"))
            .setId_series("CJ")
            .setId_number(123454l)
            .setPnc("5100313010091")
            .setAddress("home")
            .build();
    public static final User CUSTOMER_USER = new UserBuilder()
            .setUsername("CUSTOMER_USER@yahoo.com")
            .setPassword(encodePassword("Pass123"))
            .setId_series("CJ")
            .setId_number(123455l)
            .setPnc("2520707170037")
            .setAddress("home")
            .build();
    public static final User EMPLOYEE_USER = new UserBuilder()
            .setUsername("EMPLOYEE_USER@yahoo.com")
            .setPassword(encodePassword("Pass123!"))
            .setId_series("CJ")
            .setId_number(123456l)
            .setPnc("2920626240099")
            .setAddress("home")
            .build();
  }

  public static class Accounts {
    public static final Account LEI = new AccountBuilder()
            .setType("Lei")
            .setAmountOfMoney(80.50f)
            .build();
    public static final Account EURO = new AccountBuilder()
            .setType("Euro")
            .setAmountOfMoney(110.50f)
            .build();
  }
}
