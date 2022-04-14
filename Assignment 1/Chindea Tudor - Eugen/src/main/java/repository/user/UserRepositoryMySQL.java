package repository.user;

import controller.Response;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.security.RolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import static database.Constants.Tables.*;
import static java.util.Collections.singletonList;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RolesRepository rolesRepository;


    public UserRepositoryMySQL(Connection connection, RolesRepository rolesRepository) {
        this.connection = connection;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<User> findAll() {
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
                    .setRoles(rolesRepository.findRolesForUser(userResultSet.getLong("id")))
                    .build();

            return user;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchEmployeeSql = "Select * from " + USER + " where `id`=\'" + id + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchEmployeeSql);
            userResultSet.next();

            User user = new UserBuilder()
                    .setUsername(userResultSet.getString("username"))
                    .setPassword(userResultSet.getString("password"))
                    .setRoles(rolesRepository.findRolesForUser(userResultSet.getLong("id")))
                    .build();

            return user;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public void updateEmployeeUsername(Long id, String username) {
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE user SET username = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setString(1, username);
            updateAccountStatement.setLong(2, id);
            updateAccountStatement.executeUpdate();
        }catch (SQLException e){

        }
    }

    @Override
    public List<User> findEmployee() {
//        try {
//            Statement statement = connection.createStatement();
//
//            String fetchUserSql =
//                    "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
//            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
//            userResultSet.next();
//
//            User user = new UserBuilder()
//                    .setUsername(userResultSet.getString("username"))
//                    .setPassword(userResultSet.getString("password"))
//                    .setRoles(rolesRepository.findRolesForUser(userResultSet.getLong("id")))
//                    .build();
//
//            return user;
//        } catch (SQLException e) {
//            System.out.println(e.toString());
//        }
        return null;
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
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void removeById(Long id) {
        try{
            Statement statement = connection.createStatement();
            String fetchEmployeeSql = "Select * from " + USER + " where `id`=\'" + id + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchEmployeeSql);
            userResultSet.next();

            List<Role> roles = rolesRepository.findRolesForUser(userResultSet.getLong("id"));
            if(roles.get(0).getRole().equals("employee")) {
                String sql = "DELETE from user where `id` = \'" + id + "\'";
                statement.executeUpdate(sql);
            }

        }catch(SQLException e){
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