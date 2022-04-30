package repository.Security;

import model.Right;
import model.Role;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;


public class SecurityRepositoryMySQL implements SecurityRepository {

    private final Connection connection;

    public SecurityRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addRole(String role) {
        String sql = "INSERT IGNORE INTO " + ROLE + " values (null, ?)";

        try {
            PreparedStatement insertStatement = connection.prepareStatement(sql);
            insertStatement.setString(1, role);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Override
    public Role findRoleByTitle(String role) {
        String sql = "Select * from " + ROLE + " where `role`=\'" + role + "\'";

        try {
            Statement statement = connection.createStatement();
            ResultSet roleResultSet = statement.executeQuery(sql);
            roleResultSet.next();
            Long roleId = roleResultSet.getLong("id");
            String roleTitle = roleResultSet.getString("role");
            return new Role(roleId, roleTitle, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Role findRoleById(Long roleId) {
        String sql = "Select * from " + ROLE + " where `id`=\'" + roleId + "\'";

        try {
           Statement statement = connection.createStatement();
            ResultSet roleResultSet = statement.executeQuery(sql);
            roleResultSet.next();
            String roleTitle = roleResultSet.getString("role");
            return new Role(roleId, roleTitle, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void addRolesToUser(User user, List<Role> roles) {
        String sql = "INSERT INTO `user_role` values (null, ?, ?)";

        try {
            for (Role role : roles) {
                PreparedStatement insertUserRoleStatement = connection
                        .prepareStatement(sql);
                insertUserRoleStatement.setLong(1, user.getId());
                insertUserRoleStatement.setLong(2, role.getId());
                insertUserRoleStatement.executeUpdate();
            }
        } catch (SQLException e) {

        }

    }

    @Override
    public List<Role> findRolesForUser(Long userId) {
        String sql = "Select * from " + USER_ROLE + " where `user_id`=\'" + userId + "\'";
        List<Role> roles = new ArrayList<>();

        try{
            Statement statement = connection.createStatement();
            ResultSet userRoleResultSet = statement.executeQuery(sql);
            while (userRoleResultSet.next()) {
                long roleId = userRoleResultSet.getLong("role_id");
                roles.add(findRoleById(roleId));
            }
            return roles;
        } catch (SQLException e) {

        }
        return null;

    }

    @Override
    public void addRight(String right) {
        String sql = "INSERT IGNORE INTO `" + RIGHT + "` values (null, ?)";

        try {
            PreparedStatement insertStatement = connection.prepareStatement(sql);
            insertStatement.setString(1, right);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Override
    public Right findRightByTitle(String right) {
        String sql ="Select * from `" + RIGHT + "` where `right`=\'" + right + "\'";

        try {
            Statement statement = connection.createStatement();
            ResultSet rightResultSet = statement.executeQuery(sql);
            rightResultSet.next();
            Long rightId = rightResultSet.getLong("id");
            String rightTitle = rightResultSet.getString("right");
            return new Right(rightId, rightTitle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addRoleRight(Long roleId, Long rightId) {
        String sql = "INSERT IGNORE INTO " + ROLE_RIGHT + " values (null, ?, ?)";

        try {
            PreparedStatement insertStatement = connection.prepareStatement(sql);
            insertStatement.setLong(1, roleId);
            insertStatement.setLong(2, rightId);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }
}
