package bussiness_layer.service.admin;

import bussiness_layer.models.Action;
import bussiness_layer.models.User;
import repository_layer.repository.action.ActionRepository;
import repository_layer.repository.user.UserRepository;
import repository_layer.repository.user_role.UserRoleRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Date;
import java.util.List;

public class AdminServiceMySQL implements AdminService {

  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;
  private final ActionRepository actionRepository;

  public AdminServiceMySQL(UserRepository userRepository, UserRoleRepository userRoleRepository, ActionRepository actionRepository) {
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.actionRepository = actionRepository;
  }

  private static String encodePassword(String password) {
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
  public List<User> getAllEmployees() {
    return userRoleRepository.getUsersByRole("employee");
  }

  @Override
  public boolean addEmployee(User user) {
    user.setPassword(encodePassword(user.getPassword()));
    return userRoleRepository.insert("employee", user);
  }

  @Override
  public boolean updateEmployee(String username, User newUser) {
    return userRepository.update(username, newUser);
  }

  @Override
  public boolean deleteEmployee(String username) {
    return userRepository.remove(username);
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public List<Action> getReport(Date startDate, Date endDate, String username) {
    return actionRepository.retrieveAllByDate(startDate,endDate,username);
  }
}
