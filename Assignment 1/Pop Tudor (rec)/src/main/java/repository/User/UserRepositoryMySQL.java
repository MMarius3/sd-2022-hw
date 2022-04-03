package repository.User;

import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.Security.SecurityRepository;

import static database.Constants.Tables.USER;

import java.sql.*;
import java.util.List;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final SecurityRepository securityRepository;

    public UserRepositoryMySQL(Connection connection, SecurityRepository securityRepository) {
        this.connection = connection;
        this.securityRepository = securityRepository;
    }

    @Override
    public List<User> findAll() {
        return null;
    }


    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(securityRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database");
        }
        return findByUsernameAndPasswordNotification;
    }


    @Override
    public boolean save(User user) {
        String sql = "INSERT INTO user values (null, ?, ?)";

        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet resultSet = insertUserStatement.getGeneratedKeys();
            resultSet.next();
            long userId = resultSet.getLong(1);
            user.setId(userId);

            securityRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        String sql = "DELETE from user where id >= 0";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
