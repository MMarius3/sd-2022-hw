package database;

import database.enums.RightType;
import database.enums.RoleType;
import database.enums.TableTypeSQL;

import java.util.*;

import static database.DBConnectionFactory.PRODUCTION;
import static database.DBConnectionFactory.TEST;
import static database.enums.RightType.*;
import static database.enums.RoleType.ADMINISTRATOR;
import static database.enums.RoleType.EMPLOYEE;

public class Constants {
  public static final TableTypeSQL[] ORDERED_TABLES_FOR_CREATION = TableTypeSQL.values();
  public static final String[] SCHEMAS = {PRODUCTION, TEST};
  public static final RoleType[] ROLE_TYPES = RoleType.values();
  public static final RightType[] RIGHT_TYPES = RightType.values();

  public static Map<RoleType, List<RightType>> getRoleRights() {
    Map<RoleType, List<RightType>> rolesRights = new HashMap<>();
    for(RoleType roleType : ROLE_TYPES) {
      rolesRights.put(roleType, new ArrayList<>());
    }

    rolesRights.get(ADMINISTRATOR).addAll(getAdministratorRights());
    rolesRights.get(EMPLOYEE).addAll(getEmployeeRights());

    return rolesRights;
  }
}
