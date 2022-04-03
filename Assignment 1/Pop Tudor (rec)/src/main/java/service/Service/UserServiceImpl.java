package service.Service;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.Security.SecurityRepository;
import repository.User.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;

    public UserServiceImpl(UserRepository userRepository, SecurityRepository securityRepository) {
        this.userRepository = userRepository;
        this.securityRepository = securityRepository;
    }


    @Override
    public Notification<Boolean> register(String username, String password) {

        if (!username.equals("admin@yahoo.com")) {
            Role role = securityRepository.findRoleByTitle(EMPLOYEE);
            User user = new UserBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setRoles(Collections.singletonList(role))
                    .build();

            UserValidator userValidator = new UserValidator(user);
            boolean userValid = userValidator.validate();
            Notification<Boolean> userRegisterNotification = new Notification<>();

            if (!userValid) {
                userValidator.getErrors().forEach(userRegisterNotification::addError);
                userRegisterNotification.setResult(Boolean.FALSE);
            } else {
                user.setPassword(encodePassword(password));
                userRegisterNotification.setResult(userRepository.save(user));
            }
            return userRegisterNotification;
        }else{
            Role role = securityRepository.findRoleByTitle(ADMINISTRATOR);
            User user = new UserBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setRoles(Collections.singletonList(role))
                    .build();

            UserValidator userValidator = new UserValidator(user);
            boolean userValid = userValidator.validate();
            Notification<Boolean> userRegisterNotification = new Notification<>();

            if (!userValid) {
                userValidator.getErrors().forEach(userRegisterNotification::addError);
                userRegisterNotification.setResult(Boolean.FALSE);
            } else {
                user.setPassword(encodePassword(password));
                userRegisterNotification.setResult(userRepository.save(user));
            }
            return userRegisterNotification;
        }
    }

    @Override
    public Notification<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, encodePassword(password));
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


    @Override
    public boolean logout(User user) {
        return false;
    }
}
