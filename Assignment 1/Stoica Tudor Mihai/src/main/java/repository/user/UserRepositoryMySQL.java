package repository.user;

import database.DatabaseConnectionFactory;
import dtos.ClientDTO;
import dtos.UserDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserRepositoryMySQL implements UserRepository<UserDTO> {

    private final String TABLE_NAME = "User";
    private Connection connection;

    public UserRepositoryMySQL(boolean useTestConnection) {
        connection = DatabaseConnectionFactory.getConnection(useTestConnection);
    }

    public UserRepositoryMySQL() {
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
    public long create(UserDTO object) {
        return 0;
    }

    @Override
    public UserDTO getByID(long id) {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSetToUserDTO(resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(UserDTO object) {

    }

    @Override
    public List<UserDTO> getAll() {
        return null;
    }

    private UserDTO resultSetToUserDTO(ResultSet resultSet) throws SQLException {

        return new UserDTO()
                .setId(resultSet.getLong("id"))
                .setName(resultSet.getString("name"));
    }

    @Override
    public UserDTO getByName(String name) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE `name` = " + "'" + name + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSetToUserDTO(resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
