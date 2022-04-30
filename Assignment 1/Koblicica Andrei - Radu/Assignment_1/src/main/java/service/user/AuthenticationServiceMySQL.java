package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

import static database.Constants.Roles.EMPLOYEE;

public class AuthenticationServiceMySQL implements AuthenticationService {

  private final UserRepository userRepository;
  private final RightsRolesRepository rightsRolesRepository;
  private final Connection connection;

  public AuthenticationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository, Connection connection) {
    this.userRepository = userRepository;
    this.rightsRolesRepository = rightsRolesRepository;
    this.connection=connection;
  }

  @Override
  public User register(String username, String password) {
    String encodedPassword = encodePassword(password);

    Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

    User user = new UserBuilder()
        .setUsername(username)
        .setPassword(encodedPassword)
        .setRoles(Collections.singletonList(customerRole))
        .build();

    if(userRepository.save(user)){
      return user;
    }
      return null;
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
  public String getUserType(Long id) {
    try {
      PreparedStatement statement = connection
              .prepareStatement(" SELECT role FROM role JOIN user_role ON role.id = user_role.role_id WHERE user_id=?");
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      if(resultSet.next()) {
        return resultSet.getString("role");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return null;
  }

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
