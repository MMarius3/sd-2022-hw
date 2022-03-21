package repository.user;

import controller.Response;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;
import static java.util.Collections.singletonList;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightRolesRepository;

    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightRolesRepository) {
        this.connection = connection;
        this.rightRolesRepository = rightRolesRepository;
    }

    @Override
    public String getPassword(String username){
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            userResultSet.next();

            return userResultSet.getString("password");

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;

    }

    @Override
    public User findById(Long id){
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql = "Select * from `" + USER + "` where `id`=\'" + id + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            userResultSet.next();

            User user = new UserBuilder()
                    .setId(id)
                    .setUserName(userResultSet.getString("username"))
                    .setPassword(userResultSet.getString("password"))
                    .setRoles(rightRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                    .build();
            return user;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql = "Select * from `" + USER + "`";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);

            List<User> userList = new ArrayList<>();

            while(userResultSet.next()){
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUserName(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            userResultSet.next();

            User user = new UserBuilder()
                    .setId(userResultSet.getLong("id"))
                    .setUserName(userResultSet.getString("username"))
                    .setPassword(userResultSet.getString("password"))
                    .setRoles(rightRolesRepository.findRolesForUser(userResultSet.getLong("id")))
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
            PreparedStatement insertUserStatement = connection.prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try{
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public Response<Boolean> existsByUsername(String email) {
        try{
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + email + "\'";

            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return new Response<>(userResultSet.next());
        } catch (SQLException e){
            return new Response<>(singletonList(e.getMessage()));
        }
    }

    @Override
    public boolean update(User user) {
        try{
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE user SET username =?, password =? WHERE id =?");

            updateStatement.setString(1, user.getUsername());
            updateStatement.setString(2, user.getPassword());
            updateStatement.setLong(3, user.getId());
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
