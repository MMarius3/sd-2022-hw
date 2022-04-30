package repository.employee;

import model.User;
import model.builder.UserBuilder;
import repository.security.RolesRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;
import static database.Constants.Tables.USER_ROLE;

public class EmployeeRepositoryMySQL implements EmployeeRepository {
    private final Connection connection;
    private final RolesRepository rolesRepository;

    public EmployeeRepositoryMySQL(Connection connection, RolesRepository rolesRepository) {
        this.connection = connection;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<User> findAllEmployees() {

        try {
            ArrayList<User> users = new ArrayList<>();
            Statement statement = connection.createStatement();

            String fetchUserSql = "Select * from user ";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            while (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong(1))
                        .setUsername(userResultSet.getString(2))
                        .setPassword(userResultSet.getString(3))
                        .setRoles(rolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                if (user.getRoles().get(0).getRole().equals("employee")) {
                    users.add(user);
                }
            }
            return users;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean deleteEmployee(long id) {
        try {
            PreparedStatement deleteAccStatement = connection
                    .prepareStatement("DELETE FROM user WHERE id = " + id);
            deleteAccStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateEmployee(User employee) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE " + USER + " SET username = ?, password = ? WHERE id = ?");
            insertUserStatement.setString(1, employee.getUsername());
            insertUserStatement.setString(2, encodePassword(employee.getPassword()));
            insertUserStatement.setString(3, String.valueOf(employee.getId()));


            insertUserStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public boolean addEmployee(User employee) {
        try {
            PreparedStatement insertAccountStatement = connection
                    .prepareStatement("INSERT INTO " + USER + " values (null, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            insertAccountStatement.setString(1, employee.getUsername());
            insertAccountStatement.setString(2, encodePassword(employee.getPassword()));
            insertAccountStatement.executeUpdate();

            ResultSet rs = insertAccountStatement.getGeneratedKeys();
            rs.next();
            long clientId = rs.getLong(1);
            addRoleToUser(clientId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void addRoleToUser(long employeeId) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO " + USER_ROLE + " values (null, ?, ?)");
            insertUserStatement.setLong(1, employeeId);
            insertUserStatement.setLong(2, 2);

            insertUserStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
