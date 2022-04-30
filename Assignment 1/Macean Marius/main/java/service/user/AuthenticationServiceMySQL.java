package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class AuthenticationServiceMySQL implements AuthenticationService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public boolean register(String username, String password) {
        String encodedPassword = encodePassword(password);

        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(encodedPassword)
                .setRoles(Collections.singletonList(employeeRole))
                .build();

        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id %d not found".formatted(id)));
    }

    @Override
    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, encodePassword(password));
    }

    public boolean isEmployee(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, encodePassword(password));
        if (user != null) {
            if (user.getRoles().get(0).getRole().equals("employee")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean update(Long id, String name) {
        return userRepository.update(id, name);
    }

    @Override
    public boolean delete(Long id) {
        return userRepository.delete(id);
    }

    @Override
    public boolean logout(User user) {
        return false;
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

    public boolean addEmployee(String name) {
        User user = new User();
        user.setUsername(name);
        user.setPassword("Pa$$N0tSet");
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        List<Role> roles = new ArrayList<>();
        roles.add(employeeRole);
        user.setRoles(roles);
        return save(user);
    }

    public boolean updateEmployee(Long id, String name) {
        return update(id, name);
    }

    public User viewEmployee(Long id) {
        return findById(id);
    }

    public boolean deleteEmployee(Long id) {
        return delete(id);
    }

    private void createFile(User user) {
        try {
            FileWriter myWriter = new FileWriter("reports.txt");
            myWriter.write("Id: " + user.getId() + "\n");
            myWriter.write("Username: " + user.getUsername() + "\n");
            myWriter.write("Roles: " + user.getRoles() + "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean generateReport(Long id) {
        User user = findById(id);
        createFile(user);
        return true;
    }
}