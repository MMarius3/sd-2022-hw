package service.user;

import database.enums.RoleType;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.util.Collections;

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
  public boolean register(String username, String password) {
    String encodedPassword = encodePassword(password);
    Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE.getLabel())
            .orElseThrow(() -> new IllegalArgumentException(format("No role for title: %s found.", EMPLOYEE.getLabel())));

    User user = new UserBuilder()
            .setUsername(username)
            .setPassword(encodedPassword)
            .setRoles(Collections.singletonList(customerRole))
            .build();

    return userRepository.save(user);
  }

  @Override
  public User login(String username, String password) {
    return userRepository.findByUsernameAndPassword(username, encodePassword(password))
            .orElseThrow(() -> new IllegalArgumentException("Wrong username or password!"));
  }
}
