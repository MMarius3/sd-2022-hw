package repository;

import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static common.TestEntityGenerator.*;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryMySQLTest {

  private static UserRepository userRepository;
  private static JDBConnectionWrapper connectionWrapper;

  @BeforeAll
  static void setUp() {
    connectionWrapper = DBConnectionFactory.getConnectionWrapper(true);
    RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
    userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);
  }

  @AfterAll
  static void tearDown() throws SQLException {
    userRepository.deleteAll();
    connectionWrapper.getConnection().close();
  }

  @BeforeEach
  void clean() {
    userRepository.deleteAll();
  }

  @Test
  void findAll() {
    final List<User> users = userRepository.findAll();
    assertTrue(users.isEmpty());

    int nrInsertedUsers = 10;
    for(int i = 1; i <= nrInsertedUsers; i++) {
      assertTrue(userRepository.save(generateUserForTest((long) i)));
    }

    final List<User> insertedUsers = userRepository.findAll();
    assertEquals(nrInsertedUsers, insertedUsers.size());
  }

  @Test
  void findById_Empty_InvalidId() {
    final Optional<User> user = userRepository.findById(-1L);
    assertTrue(user.isEmpty());
  }

  @Test
  void findById_Present_ValidId() {
    long validId = 1L;
    User user = generateUserForTest(1L);
    assertTrue(userRepository.save(user));
    final Optional<User> userOptional = userRepository.findById(validId);

    assertTrue(userOptional.isPresent());
    assertEquals(user.getUsername(), userOptional.get().getUsername());
  }

  @Test
  void save_invalid() {
    final User invalidUser = new UserBuilder().build();
    assertFalse(userRepository.save(invalidUser));
  }

  @Test
  void save_valid() {
    final User noGivenIdUser = generateUserForTest(0L);
    assertTrue(userRepository.save(noGivenIdUser));

    final User givenIdUser = generateUserForTest(1L);
    assertTrue(userRepository.save(givenIdUser));
  }

  @Test
  void update() {
    final User user = generateUserForTest(1L);
    assertTrue(userRepository.save(user));

    final User updatedUser = generateUserForTest(2L);
    assertNotEquals(user.getUsername(), updatedUser.getUsername());
    assertTrue(userRepository.update(1L, updatedUser));

    final Optional<User> userOptional = userRepository.findById(1L);
    assertTrue(userOptional.isPresent());
    assertEquals(user.getId(), userOptional.get().getId()); // id did not change
    assertEquals(updatedUser.getUsername(), userOptional.get().getUsername());

  }

  @Test
  void deleteById() {
    final User user = generateUserForTest(1L);
    assertTrue(userRepository.save(user));
    assertTrue(userRepository.deleteById(1L));

    assertTrue(userRepository.findById(1L).isEmpty());
  }

  @Test
  void deleteAll() {
    int nrInsertedUsers = 10;
    for(int i = 1; i <= nrInsertedUsers; i++) {
      User user = generateUserForTest((long) i);
      assertTrue(userRepository.save(user));
    }

    userRepository.deleteAll();
    final List<User> allUser = userRepository.findAll();
    assertTrue(allUser.isEmpty());
  }

  @Test
  void findByUsernameAndPassword_invalid() {
    final User user = generateUserForTest(0L);
    assertTrue(userRepository.save(user));

    final Optional<User> userOptional = userRepository.findByUsernameAndPassword("invalid", user.getPassword());
    assertTrue(userOptional.isEmpty());
  }

  @Test
  void findByUsernameAndPassword_valid() {
    final User user = generateUserForTest(0L);
    assertTrue(userRepository.save(user));

    final Optional<User> userOptional = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    assertTrue(userOptional.isPresent());
    assertEquals(user.getUsername(), userOptional.get().getUsername());
    assertEquals(user.getPassword(), userOptional.get().getPassword());
  }
}