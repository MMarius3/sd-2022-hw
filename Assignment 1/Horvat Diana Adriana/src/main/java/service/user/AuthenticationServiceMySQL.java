package service.user;

import model.Role;
import model.Sentinel;
import model.User;
import model.builder.UserBuilder;
import respository.security.RolesRepository;
import respository.user.UserRepository;
import service.user.AuthenticationService;

import java.lang.annotation.Native;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;

public class AuthenticationServiceMySQL implements AuthenticationService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    private Sentinel sentinel;
    private final String EMPLOYEE = "employee";

    public AuthenticationServiceMySQL(UserRepository userRepository, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Sentinel getSentinel(){
        return this.sentinel;
    }

    @Override
    public void setSentinel(Sentinel sentinel){
        this.sentinel = sentinel;
    }

    @Override
    public boolean register(String username, String password) {
        String encodedPassword = encodePassword(password);

        Role employeeRole = rolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(encodedPassword)
                .setRoles(Collections.singletonList(employeeRole))
                .build();

        rolesRepository.addRolesToUser(user, user.getRoles());

        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, encodePassword(password));
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
}
