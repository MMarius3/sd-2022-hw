package database.enums;

public enum TableTypeSQL {
  USER("user"),
  CLIENT("client"),
  ACCOUNT("account"),
  ROLE("role"),
  RIGHT("`right`"),
  ACTIVITY("activity"),
  ROLE_RIGHT("role_right"),
  USER_ROLE("user_role");

  private final String label;

  TableTypeSQL(String label) {
    this.label = label;
  }

  public String getLabel() {
    return this.label;
  }
}
