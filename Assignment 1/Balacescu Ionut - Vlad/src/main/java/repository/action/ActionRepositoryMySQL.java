package repository.action;

import model.ActionEmployee;
import model.builder.ActionBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActionRepositoryMySQL implements ActionRepository{

    private final Connection connection;

    public ActionRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<ActionEmployee> findActionForClient() {
        try {
            List<ActionEmployee> actionEmployees = new ArrayList<>();
            Statement statement = connection.createStatement();

            String fetchRoleSql = "Select * from actions";
            ResultSet actionResult = statement.executeQuery(fetchRoleSql);
            while (actionResult.next()) {
                ActionEmployee actionEmployee = new ActionBuilder().setId(actionResult.getLong("id"))
                        .setUsername(actionResult.getString("username"))
                        .setName(actionResult.getString("name"))
                        .setDate(actionResult.getString("date")).build();
                actionEmployees.add(actionEmployee);
            }
            return actionEmployees;
        } catch (SQLException e) {

        }
        return null;

    }

    @Override
    public void addAction(ActionEmployee action) {
        try {
            PreparedStatement insertAccountStatement = connection
                    .prepareStatement("INSERT INTO actions values (null, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            insertAccountStatement.setString(1, action.getUsername());
            insertAccountStatement.setString(2, action.getName());
            insertAccountStatement.setString(3, action.getDate());
            insertAccountStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
