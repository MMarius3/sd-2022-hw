package repository.security;

import model.Role;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.ROLE;
import static database.Constants.Tables.USER_ROLE;

public class RoleRepositoryMySQL implements RoleRepository {
    private final Connection connection;

    public RoleRepositoryMySQL(Connection connection) {
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
            e.printStackTrace();
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
            int roleId = roleResultSet.getInt("id");
            String roleTitle = roleResultSet.getString("role");
            return new Role(roleId, roleTitle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Role findRoleById(int roleId) {
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
    public void addRolesToUser(User user, List<Role> roles) {
        try {
            for (Role role : roles) {
                PreparedStatement insertUserRoleStatement = connection
                        .prepareStatement("INSERT INTO `user_role` values (null, ?, ?)");
                insertUserRoleStatement.setLong(1, user.getId());
                insertUserRoleStatement.setLong(2, role.getId());
                insertUserRoleStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Role> findRolesForUser(Long userId) {
        try {
            List<Role> roles = new ArrayList<>();
            Statement statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + USER_ROLE + " where `user_id`=\'" + userId + "\'";
            ResultSet userRoleResultSet = statement.executeQuery(fetchRoleSql);
            while (userRoleResultSet.next()) {
                int roleId = userRoleResultSet.getInt("role_id");
                roles.add(findRoleById(roleId));
            }
            return roles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
