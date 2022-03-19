package respository.user;

import controller.Response;
import model.User;
import model.builder.UserBuilder;
import respository.security.RolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

public class UserRepositoryMySQL implements UserRepository{
    private final Connection connection;
    private final RolesRepository rolesRepository;
    public static final String USER = "users";

    public UserRepositoryMySQL(Connection connection, RolesRepository rolesRepository) {
        this.connection = connection;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<User> findAll() {
        try {
            Statement statement = connection.createStatement();

            String getAllUsersSql =
                    "Select * from `" + USER + "`";
            ResultSet userResultSet = statement.executeQuery(getAllUsersSql);

            List<User> users = new ArrayList<>();

            while(userResultSet.next()){
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rolesRepository.findRolesForUser(userResultSet.getInt("id")))
                        .build();

                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            userResultSet.next();

            User user = new UserBuilder()
                    .setUsername(userResultSet.getString("username"))
                    .setPassword(userResultSet.getString("password"))
                    .setRoles(rolesRepository.findRolesForUser(userResultSet.getInt("id")))
                    .build();

            return user;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO users values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            int userId = rs.getInt(1);
            user.setId(userId);

            rolesRepository.addRolesToUser(user, user.getRoles());

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
            String sql = "DELETE from users where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response<Boolean> existsByUsername(String email) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + USER + "` where `username`=\'" + email + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(userResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }

}
