package repository.user;

//import controller.Response;
import controller.Response;
import model.Account;
import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;
import static database.Constants.Tables.USER;
import static java.util.Collections.singletonList;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        try {
            Statement statement = connection.createStatement();

            String fetchClientSql =
                    "Select * from " + USER;
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            List<User> users = new ArrayList<>();
            while(clientResultSet.next()) {
                Long id = clientResultSet.getLong("id");
                String username = clientResultSet.getString("username");

                User user = new UserBuilder()
                        .setId(id)
                        .setUsername(username)
                        .build();
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + USER + "` where `id`=\'" + id + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            userResultSet.next();

            User user = new UserBuilder()
                    .setId(id)
                    .setUsername(userResultSet.getString("username"))
                    .setPassword(userResultSet.getString("password"))
                    .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                    .build();

            return user;
        } catch (SQLException e) {
            System.out.println(e);
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
                    .setId(userResultSet.getLong("id"))
                    .setUsername(userResultSet.getString("username"))
                    .setPassword(userResultSet.getString("password"))
                    .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                    .build();

            return user;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Response<Boolean> existsByUsername(String username) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + USER + "` where `username`=\'" + username + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(userResultSet.next());
        } catch (SQLException e) {
            return new Response<>(singletonList(e.getMessage()));
        }
    }


    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from `" + USER + "` where `id`=\'" + id + "\'";
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long id, User newUser) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE " + USER + " SET username = ?, password = ? " +
                    "WHERE `id`=\'" + id + "\';");

            statement.setString(1, newUser.getUsername());
            statement.setString(2, newUser.getPassword());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Update user error");
            System.out.println(e);
            return false;
        }
    }

//    @Override
//    public Response<Boolean> existsByUsername(String email) {
//        try {
//            Statement statement = connection.createStatement();
//
//            String fetchUserSql =
//                    "Select * from `" + USER + "` where `username`=\'" + email + "\'";
//            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
//            return new Response<>(userResultSet.next());
//        } catch (SQLException e) {
//            return new Response<>(singletonList(e.getMessage()));
//        }
//    }
}