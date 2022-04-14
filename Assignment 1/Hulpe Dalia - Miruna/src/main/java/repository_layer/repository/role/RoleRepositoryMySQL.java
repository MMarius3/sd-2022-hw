package repository_layer.repository.role;

import bussiness_layer.builder.RoleBuilder;
import bussiness_layer.models.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static repository_layer.database_builder.Constants.Tables.ROLE;

public class RoleRepositoryMySQL implements RoleRepository {

  private final Connection connection;


  public RoleRepositoryMySQL(Connection connection)

  {
    this.connection = connection;
  }


  @Override
  public boolean insert(Role role) {
    try {
      PreparedStatement insertStatement = connection
              .prepareStatement("INSERT INTO " + ROLE + " values (null, ?)");
      insertStatement.setString(1, role.getRole());
      insertStatement.executeUpdate();

      return true;
    } catch (SQLException e) {
    }
    return false;
  }

  @Override
  public Role findByName(String name) {
    try {
      PreparedStatement findStatement = connection
              .prepareStatement("SELECT * FROM " + ROLE + " WHERE `role` = \'"+name+"\'");

      ResultSet roleResultSet = findStatement.executeQuery();
      roleResultSet.next();
      Role role = new RoleBuilder()
              .setId(roleResultSet.getLong("id"))
              .setRole(roleResultSet.getString("role"))
              .build();

      return role;
    } catch (SQLException e) {
    }
    return null;
  }

  @Override
  public Role findById(Long id) {
    try {
      PreparedStatement findStatement = connection
              .prepareStatement("SELECT * FROM " + ROLE + " WHERE `id` = "+id);

      ResultSet roleResultSet = findStatement.executeQuery();
      roleResultSet.next();
      Role role = new RoleBuilder()
              .setId(roleResultSet.getLong("id"))
              .setRole(roleResultSet.getString("role"))
              .build();

      return role;
    } catch (SQLException e) {
    }
    return null;
  }
}
