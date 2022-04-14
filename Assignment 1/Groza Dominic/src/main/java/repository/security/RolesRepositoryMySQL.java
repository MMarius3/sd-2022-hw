package repository.security;

import model.Role;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import static database.Constants.Tables.ROLE;

import static database.Constants.Tables.USER_ROLE;

public class RolesRepositoryMySQL implements RolesRepository {

    private final Connection connection;

    public RolesRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addRole(String role) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + ROLE + " values (null, ?)");
            insertStatement.setString(1, role);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }


    @Override
    public Role findRoleByTitle(String role) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ROLE + " where `role`=\'" + role + "\'";
            ResultSet roleResultSet = statement.executeQuery(fetchRoleSql);
            roleResultSet.next();
            Long roleId = roleResultSet.getLong("id");
            String roleTitle = roleResultSet.getString("role");
            return new Role(roleId, roleTitle);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Role findRoleById(Long roleId) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ROLE + " where `id`=\'" + roleId + "\'";
            ResultSet roleResultSet = statement.executeQuery(fetchRoleSql);
            roleResultSet.next();
            String roleTitle = roleResultSet.getString("role");
            return new Role(roleId, roleTitle);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void addRoleToUser(User user, Role role) {
        try {
            PreparedStatement insertUserRoleStatement = connection
                    .prepareStatement("INSERT INTO `user_role` values (null, ?, ?)");
            insertUserRoleStatement.setLong(1, user.getId());
            insertUserRoleStatement.setLong(2, role.getId());
            insertUserRoleStatement.executeUpdate();

        } catch (SQLException e) {

        }
    }

    @Override
    public Role findRoleForUser(Long userId) {
        try {
            Statement statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + USER_ROLE + " where `user_id`=\'" + userId + "\'";
            ResultSet userRoleResultSet = statement.executeQuery(fetchRoleSql);
            if (userRoleResultSet.next()) {
                long roleId = userRoleResultSet.getLong("role_id");
                return findRoleById(roleId);
            }
        } catch (SQLException e) {
        }
        return null;
    }


}