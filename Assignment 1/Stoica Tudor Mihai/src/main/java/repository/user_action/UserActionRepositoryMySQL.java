package repository.user_action;

import database.DatabaseConnectionFactory;
import dtos.ClientDTO;
import dtos.UserActionDTO;
import dtos.UserDTO;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserActionRepositoryMySQL implements UserActionRepository<UserActionDTO> {

    private final String TABLE_NAME = "User_Action";
    private final Connection connection;

    public UserActionRepositoryMySQL(boolean useTestConnection) {
        connection = DatabaseConnectionFactory.getConnection(useTestConnection);
    }

    public UserActionRepositoryMySQL() {
        connection = DatabaseConnectionFactory.getProductionConnection();
    }

    @Override
    public void deleteByID(long id) {
        String sql = "DELETE * FROM " + TABLE_NAME + " WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE * FROM " + TABLE_NAME;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public long create(UserActionDTO object) {
        return 0;
    }

    @Override
    public UserActionDTO getByID(long id) {
        return null;
    }

    @Override
    public void update(UserActionDTO object) {

    }

    @Override
    public List<UserActionDTO> getAll() {
        return null;
    }

    @Override
    public List<UserActionDTO> getByUserID(long userID) {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = " + userID;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            List<UserActionDTO> userActionDTOs = new ArrayList<>();

            while (resultSet.next()) {
                userActionDTOs.add(resultSetToUserActionDTO(resultSet));
            }

            return userActionDTOs;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public UserActionDTO getByAction(String action) {
        return null;
    }

    private UserActionDTO resultSetToUserActionDTO(ResultSet resultSet) throws SQLException {

        UserRepository<UserDTO> userRepository = new UserRepositoryMySQL();

        return new UserActionDTO()
                .setId(resultSet.getLong("id"))
                .setUser(userRepository.getByID(resultSet.getLong("user_id")))
                .setAction(resultSet.getString("action"))
                .setActionDetails(resultSet.getString("action_details"));
    }
}
