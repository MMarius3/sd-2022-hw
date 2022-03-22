package launcher;

import database.DBConnectionFactory;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;

import java.sql.Connection;

public class ComponentFactory {
  private final RightsRolesRepository rightsRolesRepository;

  private static ComponentFactory instance;

  public static ComponentFactory instance(Boolean componentsForTests) {
    if(instance == null) {
      instance = new ComponentFactory(componentsForTests);
    }
    return instance;
  }

  private ComponentFactory(Boolean componentsForTests) {
    Connection connection = DBConnectionFactory.getConnectionWrapper(componentsForTests).getConnection();
    this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
  }

  public RightsRolesRepository getRightsRolesRepository() {
    return rightsRolesRepository;
  }
}
