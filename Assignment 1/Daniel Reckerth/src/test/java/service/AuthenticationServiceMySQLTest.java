package service;

import database.DBConnectionFactory;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceMySQLTest {

  private static AuthenticationService authenticationService;
  private static UserRepository userRepository;

  @BeforeAll
  public static void setUp() {
    Connection connection = DBConnectionFactory.getConnectionWrapper(true).getConnection();
    RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
    userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

    authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
  }

  @BeforeEach
  public void cleanUp() {
    userRepository.deleteAll();
  }

  @Test
  void register() {
    assertTrue(authenticationService.register("test_username", "test_password"));
  }

  @Test
  void login() {
    String username = "test_username";
    String password = "test_password";

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {authenticationService.login(username, password);});
    assertEquals("Wrong username or password!", illegalArgumentException.getMessage());

    assertTrue(authenticationService.register(username, password));

    User user = authenticationService.login(username, password);
    assertNotNull(user);
    assertEquals(username, user.getUsername());
  }
}