package database.enums;

import java.util.*;

public enum RightType {
  CREATE_EMPLOYEE("create-employee"),
  VIEW_EMPLOYEE("view-employee"),
  DELETE_EMPLOYEE("delete-employee"),
  UPDATE_EMPLOYEE("update-employee"),

  CREATE_CLIENT("create-client"),
  UPDATE_CLIENT("update-client"),
  VIEW_CLIENT("view-client"),

  CREATE_ACCOUNT("create-account"),
  VIEW_ACCOUNT("view-account"),
  UPDATE_ACCOUNT("update-account"),
  DELETE_ACCOUNT("delete-account"),

  TRANSFER_MONEY("transfer-money"),
  PROCESS_UTILITIES_BILLS("process-utilities-bills"),
  GENERATE_REPORTS("generate-reports");

  private final String label;
  private static final Map<String, RightType> BY_LABEL = new HashMap<>();
  private static final Random RANDOM = new Random();

  static {
    for(RightType rightType: values()) {
      BY_LABEL.put(rightType.label, rightType);
    }
  }

  RightType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return this.label;
  }

  public static RightType valueOfLabel(String label) {
    return BY_LABEL.get(label);
  }

  public static List<RightType> getAdministratorRights() {
    return Arrays.asList(CREATE_EMPLOYEE, VIEW_EMPLOYEE, UPDATE_EMPLOYEE, DELETE_EMPLOYEE,
            GENERATE_REPORTS);
  }

  public static List<RightType> getEmployeeRights() {
    return Arrays.asList(CREATE_CLIENT, UPDATE_CLIENT, VIEW_CLIENT,
            CREATE_ACCOUNT, VIEW_ACCOUNT, UPDATE_ACCOUNT, DELETE_ACCOUNT,
            TRANSFER_MONEY, PROCESS_UTILITIES_BILLS);
  }

  public static RightType getRandomEmployeeRight() {
    List<RightType> employeeRights = getEmployeeRights();
    return employeeRights.get(RANDOM.nextInt(employeeRights.size()));
  }

  public static RightType getRandomAdministratorRight() {
    List<RightType> adminRights = getAdministratorRights();
    return adminRights.get(RANDOM.nextInt(adminRights.size()));
  }
}
