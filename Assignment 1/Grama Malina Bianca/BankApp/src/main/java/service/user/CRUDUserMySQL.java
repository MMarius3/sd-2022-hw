package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import service.authentication.AuthenticationService;

import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class CRUDUserMySQL implements CRUDUser{

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final RightsRolesRepository rightsRolesRepository;

    public CRUDUserMySQL(UserRepository userRepository, AuthenticationService authenticationService, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Notification<Boolean> save(User user) {
        return authenticationService.register(user.getUsername(), user.getPassword());
    }

    @Override
    public Notification<Boolean> update(Long id, String username, String unEncodedPass) {
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(unEncodedPass)
                .setRoles(Collections.singletonList(customerRole))
                .build();

        UserValidator userValidator = new UserValidator(user, userRepository);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userUpdateNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userUpdateNotification::addError);
            userUpdateNotification.setResult(Boolean.FALSE);
        } else {
            String password = authenticationService.encodePassword(unEncodedPass);
            userUpdateNotification.setResult(userRepository.update(id,username,password));
        }
        return userUpdateNotification;
    }

    @Override
    public Notification<Boolean> updateUsername(Long id, String username, String password) {
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(customerRole))
                .build();

        UserValidator userValidator = new UserValidator(user, userRepository);
        boolean userValid = userValidator.validateUser();
        Notification<Boolean> userUpdateNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userUpdateNotification::addError);
            userUpdateNotification.setResult(Boolean.FALSE);
        } else {
            userUpdateNotification.setResult(userRepository.update(id,username,password));
        }
        return userUpdateNotification;
    }

    @Override
    public Notification<Boolean> updatePassword(Long id, String username, String pass) {
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(pass)
                .setRoles(Collections.singletonList(customerRole))
                .build();

        UserValidator userValidator = new UserValidator(user, userRepository);
        boolean userValid = userValidator.validatePassword();
        Notification<Boolean> userUpdateNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userUpdateNotification::addError);
            userUpdateNotification.setResult(Boolean.FALSE);
        } else {
            String password = authenticationService.encodePassword(pass);
            userUpdateNotification.setResult(userRepository.update(id,username,password));
        }
        return userUpdateNotification;
    }


    @Override
    public User findByID(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean delete(Long id) {
        return userRepository.remove(id);
    }
}
