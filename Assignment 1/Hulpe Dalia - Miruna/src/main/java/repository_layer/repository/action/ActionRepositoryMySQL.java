package repository_layer.repository.action;

import bussiness_layer.builder.ActionBuilder;
import bussiness_layer.models.Action;
import bussiness_layer.models.User;
import repository_layer.repository.user_role.UserRoleRepository;
import repository_layer.repository.user_role.UserRoleRepositoryMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static repository_layer.database_builder.Constants.Tables.ACTION;

public class ActionRepositoryMySQL implements ActionRepository {

  private final Connection connection;
  private final UserRoleRepository userRoleRepositoryMySQL;

  public ActionRepositoryMySQL(Connection connection, UserRoleRepository userRoleRepositoryMySQL)

  {
    this.connection = connection;
    this.userRoleRepositoryMySQL = new UserRoleRepositoryMySQL(connection);
  }


  @Override
  public boolean insert(Action action, String username) {
    User user = userRoleRepositoryMySQL.getUserWithRolesUsername(username);
    try {
      PreparedStatement insertStatement = connection
              .prepareStatement("INSERT INTO " + ACTION + " values (null, ?, ?,?)");
      insertStatement.setString(1, action.getType());
      insertStatement.setDate(2, new Date(System.currentTimeMillis()));
      insertStatement.setFloat(3, user.getId());
      insertStatement.executeUpdate();

      return true;
    } catch (SQLException e) {
    }
    return false;
  }

  @Override
  public List<Action> retrieveAllByDate(Date startDate, Date endDate, String username) {
    User user = userRoleRepositoryMySQL.getUserWithRolesUsername(username);
    if (user != null || !user.getRoles().get(0).getRole().equals("employee"))
    {
      try {
        PreparedStatement findStatement = connection
                .prepareStatement("SELECT * FROM " + ACTION + " WHERE `date_of_creation` >= ?  and `date_of_creation` <= ? and `user_id` = ?");
        findStatement.setDate(1, startDate);
        findStatement.setDate(2, endDate);
        findStatement.setLong(3, user.getId());

        ResultSet actionResultSet = findStatement.executeQuery();

        List<Action> actions = new ArrayList<>();

        while (actionResultSet.next())
        {
          Action action = new ActionBuilder()
                  .setId(actionResultSet.getLong("id"))
                  .setType(actionResultSet.getString("type"))
                  .setDate(actionResultSet.getDate("date_of_creation"))
                  .setUserId(user.getId())
                  .build();
          actions.add(action);
        }

        return actions;
      } catch (SQLException e) {
      }
      return null;
    }
    return null;
  }
}
