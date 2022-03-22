package database.enums;

import java.util.HashMap;
import java.util.Map;

public enum RoleType {
  ADMINISTRATOR("administrator"),
  EMPLOYEE("employee");

  private final String label;
  private static final Map<String, RoleType> BY_LABEL = new HashMap<>();

  static {
    for(RoleType roleType: values()) {
      BY_LABEL.put(roleType.label, roleType);
    }
  }

  RoleType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public static RoleType valueOfLabel(String label) {
    return BY_LABEL.get(label);
  }
}
