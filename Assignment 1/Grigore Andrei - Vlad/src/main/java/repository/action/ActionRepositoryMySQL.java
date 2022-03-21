package repository.action;

import model.Action;
import model.User;
import model.builder.ActionBuilder;
import model.builder.UserBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class ActionRepositoryMySQL implements ActionRepository {

    private final Connection connection;

    public ActionRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }



    @Override
    public List<Action> findAllWithId(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchActionSql =
                    "Select * from " + ACTION + " where user_id = \'" + id +"\'" ;
            ResultSet actionResultSet = statement.executeQuery(fetchActionSql);
            List<Action> actions = new ArrayList<>();
            while(actionResultSet.next()) {
                Action action= new ActionBuilder()
                        .setId(actionResultSet.getLong("id"))
                        .setType(actionResultSet.getString("type"))
                        .setDescription(actionResultSet.getString("description"))
                        .build();
                actions.add(action);
            }
            return actions;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    @Override
    public List<Action> findAll() {
        try {
            Statement statement = connection.createStatement();

            String fetchActionSql =
                    "Select * from " + ACTION;
            ResultSet actionResultSet = statement.executeQuery(fetchActionSql);
            List<Action> actions = new ArrayList<>();
            while(actionResultSet.next()) {
                Action action= new ActionBuilder()
                        .setId(actionResultSet.getLong("id"))
                        .setType(actionResultSet.getString("type"))
                        .setDescription(actionResultSet.getString("description"))
                        .build();
                actions.add(action);
            }
            return actions;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean save(Action action) throws SQLException {
        try{
            PreparedStatement insertActionStatement = connection
                    .prepareStatement("INSERT INTO `"+ACTION+"` values (null, ?, ?,?)", Statement.RETURN_GENERATED_KEYS);
            insertActionStatement.setLong(1, action.getUser().getId());
            insertActionStatement.setString(2, action.getType());
            insertActionStatement.setString(3,action.getDescription());
            insertActionStatement.executeUpdate();

            ResultSet rs = insertActionStatement.getGeneratedKeys();
            rs.next();
            long actionId = rs.getLong(1);
            action.setId(actionId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from `" + ACTION + "` where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
