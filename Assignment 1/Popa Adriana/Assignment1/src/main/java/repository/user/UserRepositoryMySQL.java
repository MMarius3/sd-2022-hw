package repository.user;

import controller.Response;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

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
            System.out.println(e.toString());
        }
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

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

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

    @Override
    public boolean delete(String username) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("DELETE FROM user WHERE username = ?");
            insertStatement.setString(1,username);
            insertStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Long id,String username, String password) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE user SET username = ?, password = ? WHERE id = ?" );
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.setLong(3, id);
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try{
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM user WHERE id = ?");
            findStatement.setLong(1, id);
            ResultSet rs = findStatement.executeQuery();
            if(rs.next())
                return Optional.ofNullable(getUserFromResultSet(rs));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        return new UserBuilder()
                .setId(rs.getLong("id"))
                .setUsername(rs.getString("username"))
                .setPassword(rs.getString("password"))
                .build();
    }


}