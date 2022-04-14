package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public UserServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> createEmployee(String username, String password) {
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(employeeRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        List<User> allUsers = userRepository.findAll();
        for(User user1 : allUsers){
            if (user1.getUsername().equals(user.getUsername())) {
                userRegisterNotification.addError("Already existing id!");
                userRegisterNotification.setResult(Boolean.FALSE);
                return userRegisterNotification;
            }
        }

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(encodePassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }
        return userRegisterNotification;
    }

    @Override
    public Notification<User> viewEmployee(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Notification<Boolean> updateUser(Long id, String username) {
        User user = new UserBuilder()
                .setId(id)
                .setUsername(username)
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validateUsernameBool(username);
        Notification<Boolean> updateUserNotification = new Notification<>();

        List<User> allUsers = userRepository.findAll();
        for(User user1 : allUsers){
            if (user1.getUsername().equals(user.getUsername())) {
                updateUserNotification.addError("Already existing id!");
                updateUserNotification.setResult(Boolean.FALSE);
                return updateUserNotification;
            }
        }

        if (!userValid) {
            userValidator.getErrors().forEach(updateUserNotification::addError);
            updateUserNotification.setResult(Boolean.FALSE);
        } else {
            updateUserNotification.setResult(userRepository.update(id, username));
        }
        return updateUserNotification;
    }

    @Override
    public boolean deleteUser(User user) {
        return userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllEmployees(){ return userRepository.findAllEmployees(); }

    @Override
    public List<Role> getRoles(User user) {
        return rightsRolesRepository.findRolesForUser(user.getId());
    }

    @Override
    public void removeAll() {
        userRepository.removeAll();
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
