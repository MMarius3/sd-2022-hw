package bussiness_layer.service.authentication;

import bussiness_layer.models.User;
import bussiness_layer.service.employee.EmployeeService;
import repository_layer.repository.user.UserRepository;
import repository_layer.repository.user_role.UserRoleRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class AuthenticationServiceMySQL implements AuthenticationService {

  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;
  private final EmployeeService employeeService;

  public AuthenticationServiceMySQL(UserRepository userRepository, UserRoleRepository userRoleRepository, EmployeeService employeeService) {
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.employeeService = employeeService;
  }

  @Override
  public User login(String username, String password) {
    User currentUser = userRoleRepository.getUserWithRoles(username, encodePassword(password));
    employeeService.setUsername(currentUser.getUsername());
    return currentUser;
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
