package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import service.user.AuthenticationService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
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
        String encodePassword = encodePassword(password);

        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder()
                .setUserName(username)
                .setPassword(encodePassword)
                .setRoles(Collections.singletonList(customerRole))
                .build();
        return userRepository.save(user);
    }

    @Override
    public boolean update(User user){
        String encodePassword = encodePassword(user.getPassword());
        user.setPassword(encodePassword);
        return userRepository.update(user);
    }


    @Override
    public boolean createAdministrator(String username, String password){
        String encodePassword = encodePassword(password);

        Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);

        User user = new UserBuilder()
                .setUserName(username)
                .setPassword(encodePassword)
                .setRoles(Collections.singletonList(customerRole))
                .build();
        return userRepository.save(user);

    }

    @Override
    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, encodePassword(password));
    }

    @Override
    public User findById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    @Override
    public String encodePassword(String password) {
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


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
