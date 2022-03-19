package repository.action;

import model.Action;

import java.sql.*;

import static database.Constants.Tables.ACTION;

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
            rs.next();
            long actionId = rs.getLong(1);
            action.setId(actionId);

            return true;
        } catch (SQLException e) {
            System.out.println("Error in save");
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean filterByDateAndId(Long user_id, Date from, Date to) {
        return false;
    }
}
