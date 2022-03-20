package repository.action;

import model.Action;
import model.Client;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.ACTION;
import static database.Constants.Tables.CLIENT;

public class ActionRepositoryMySQL implements ActionRepository {
    private final Connection connection;

    public ActionRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Action action) {
        try {
            PreparedStatement isertActionStatement = connection
                    .prepareStatement("INSERT INTO " + ACTION + " values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            isertActionStatement.setLong(1, action.getUser_id());
            isertActionStatement.setString(2, action.getAction());
            isertActionStatement.setDate(3, action.getDate());
            isertActionStatement.executeUpdate();

            ResultSet rs = isertActionStatement.getGeneratedKeys();
            if(rs.next()) {
                long actionId = rs.getLong(1);
                action.setId(actionId);
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error in save");
            System.out.println(e);
            return false;
        }
    }

    @Override
    public List<Action> filterByDateAndId(Long user_id, Date from, Date to) {
        try {
            Statement statement = connection.createStatement();

            String fetchActionsSql =
                    "Select * from " + ACTION + " where user_id=" + user_id + " AND " +
                            "action_date >= \'" + from + "\' and action_date <= \'" + to + "\'";
            ResultSet actionsResultSet = statement.executeQuery(fetchActionsSql);
            List<Action> actions = new ArrayList<>();
            while(actionsResultSet.next()) {
                Long id = actionsResultSet.getLong("id");
                String actionStr = actionsResultSet.getString("action");
                Date date = actionsResultSet.getDate("action_date");

                Action action = Action.builder()
                        .id(id)
                        .user_id(user_id)
                        .action(actionStr)
                        .date(date)
                        .build();
                actions.add(action);
            }
            return actions;

        } catch (SQLException e) {
            System.out.println("Error in find all");
            System.out.println(e);
        }
        return null;
    }
}
