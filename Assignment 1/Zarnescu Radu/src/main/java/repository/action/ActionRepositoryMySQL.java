package repository.action;

import model.Action;
import model.builder.ActionBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActionRepositoryMySQL implements ActionRepository {
    private final Connection connection;

    public ActionRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Action> findActionsBetweenDates(String date1, String date2, Long id) {
        List<Action> actions = new ArrayList<>();

        String sql = "SELECT * FROM action WHERE (date BETWEEN '" + date1 + "' AND '" + date2 + "') AND employeeID = " + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                actions.add(getActionFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actions;
    }

    private Action getActionFromResultSet(ResultSet rs) throws SQLException {
        return new ActionBuilder()
                .setID(rs.getLong("id"))
                .setType(rs.getString("type"))
                .setDetails(rs.getString("details"))
                .setDate(rs.getDate("date").toLocalDate())
                .setUserID(rs.getInt("employeeID"))
                .build();
    }

    @Override
    public boolean save(Action action) {
        String sql = "INSERT INTO action VALUES (null, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, action.getType());
            preparedStatement.setString(2, action.getDetails());
            preparedStatement.setDate(3, Date.valueOf(action.getDate()));
            preparedStatement.setInt(4, action.getUserID());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
