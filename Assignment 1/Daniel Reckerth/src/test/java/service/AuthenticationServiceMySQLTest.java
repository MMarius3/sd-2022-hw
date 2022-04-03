package service;

import database.DBConnectionFactory;
import model.User;
import model.validation.Notification;
import model.validation.ResultFetchException;
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
    Notification<Boolean> authentication = authenticationService.register("daniel@gmail.com", "TestPass123");
    assertTrue(authentication.getResult());
  }

  @Test
  void login() {
    String username = "test_username";
    String password = "test_password";

    Notification<User> user = authenticationService.login(username, password);
    assertThrows(ResultFetchException.class, user::getResult);

    assertTrue(authenticationService.register("daniel@gmail.com", "TestPass123").getResult());
    Notification<User> userNotification = authenticationService.login("daniel@gmail.com", "TestPass123");
   assertEquals("daniel@gmail.com", userNotification.getResult().getUsername());
  }
}