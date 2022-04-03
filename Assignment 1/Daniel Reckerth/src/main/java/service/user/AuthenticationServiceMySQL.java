package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static database.enums.RoleType.EMPLOYEE;
import static java.lang.String.format;
import static service.user.PasswordEncoder.encodePassword;

public class AuthenticationServiceMySQL implements AuthenticationService {

  private final UserRepository userRepository;
  private final RightsRolesRepository rightsRolesRepository;

  public AuthenticationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
    this.userRepository = userRepository;
    this.rightsRolesRepository = rightsRolesRepository;
  }

  @Override
  public Notification<Boolean> register(String username, String password) {
    Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE.getLabel())
            .orElseThrow(() -> new IllegalArgumentException(format("No role for title: %s found.", EMPLOYEE.getLabel())));

    User user = new UserBuilder()
            .setUsername(username)
            .setPassword(password)
            .setRoles(Collections.singletonList(customerRole))
            .build();

    UserValidator userValidator = new UserValidator(user);
    boolean isUserValid = userValidator.validate();
    Notification<Boolean> userRegisterNotification = new Notification<>();
    if (!isUserValid) {
      userValidator.getErrors().forEach(userRegisterNotification::addError);
      userRegisterNotification.setResult(false);
    } else {
      user.setPassword(encodePassword(password));
      userRegisterNotification.setResult(userRepository.save(user));
    }
    return userRegisterNotification;
  }

  @Override
  public Notification<User> login(String username, String password) {
    Optional<User> user = userRepository.findByUsernameAndPassword(username, encodePassword(password));
    Notification<User> userLoginNotification = new Notification<>();
    if(user.isPresent()){
      userLoginNotification.setResult(user.get());
    } else {
      userLoginNotification.addError("Invalid username or password.");
    }
    return userLoginNotification;
  }
}
