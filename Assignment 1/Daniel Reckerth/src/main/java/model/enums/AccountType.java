package model.enums;

import java.util.*;

public enum AccountType {
  SAVINGS_ACCOUNT("savings"),
  CHECKING_ACCOUNT("checking"),
  CD_ACCOUNT("CDs"),
  SALARY_ACCOUNT("salary"),
  FIXED_DEPOSIT_ACCOUNT("fixed-deposit-account");

  private final String label;
  private static final Map<String, AccountType> BY_LABEL = new HashMap<>();
  private static final Random RANDOM = new Random();

  static {
    for(AccountType roleType: values()) {
      BY_LABEL.put(roleType.label, roleType);
    }
  }

  AccountType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return this.label;
  }

  public static AccountType valueOfLabel(String label) {
    return BY_LABEL.get(label);
  }

  public static AccountType getRandomAccountType() {
    AccountType[] values = AccountType.values();
    return List.of(values).get(RANDOM.nextInt(values.length));
  }
}
