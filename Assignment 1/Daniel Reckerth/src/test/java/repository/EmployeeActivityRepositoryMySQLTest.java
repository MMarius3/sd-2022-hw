package repository;


import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import model.*;
import model.builder.EmployeeActivityBuilder;
import org.junit.jupiter.api.*;
import repository.activity.EmployeeActivityRepository;
import repository.activity.EmployeeActivityRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.SQLException;
import java.util.List;

import static common.TestEntityGenerator.*;
import static database.enums.RightType.getRandomEmployeeRight;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeActivityRepositoryMySQLTest {

  private static EmployeeActivityRepository employeeActivityRepository;
  private static UserRepository userRepository;
  private static JDBConnectionWrapper connectionWrapper;

  @BeforeAll
  static void setUp() {
    connectionWrapper = DBConnectionFactory.getConnectionWrapper(true);
    RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
    userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);
    employeeActivityRepository = new EmployeeActivityRepositoryMySQL(connectionWrapper.getConnection(), userRepository, rightsRolesRepository);
  }

  @AfterAll
  static void tearDown() throws SQLException {
    connectionWrapper.getConnection().close();
  }

  @BeforeEach
  void clean() {
    employeeActivityRepository.deleteAll();
    userRepository.deleteAll();
    employeeActivityRepository.deleteAll();
  }

  @Test
  void findAll() {
    final List<EmployeeActivity> accounts = employeeActivityRepository.findAll();
    assertTrue(accounts.isEmpty());

    int nrActivities = 10;
    User user = generateUserForTest(0L);
    assertTrue(userRepository.save(user));
    for(int i = 1; i <= 10; i++) {
      Right activity = new Right((long) i, getRandomEmployeeRight());
      EmployeeActivity employeeActivity = generateEmployeeActivityForTest((long) i, user, activity);
      assertTrue(employeeActivityRepository.save(employeeActivity));
    }

    final List<EmployeeActivity> allActivities = employeeActivityRepository.findAll();
    assertEquals(nrActivities, allActivities.size());
  }

  @Test
  void save_invalid() {
    final EmployeeActivity employeeActivity = new EmployeeActivityBuilder().build();
    assertFalse(employeeActivityRepository.save(employeeActivity));
  }

  @Test
  void save_valid() {
    final User user = generateUserForTest(0L);
    assertTrue(userRepository.save(user));
    final EmployeeActivity noGivenIdEmployeeActivity = generateEmployeeActivityForTest(0L, user, new Right(1L, getRandomEmployeeRight()));
    assertTrue(employeeActivityRepository.save(noGivenIdEmployeeActivity));

    final EmployeeActivity givenIdActivity = generateEmployeeActivityForTest(13L, user, new Right(2L, getRandomEmployeeRight()));
    assertTrue(employeeActivityRepository.save(givenIdActivity));
  }

  @Test
  void deleteById() {
    final User user = generateUserForTest(0L);
    final EmployeeActivity activity = generateEmployeeActivityForTest(13L, user, new Right(1L, getRandomEmployeeRight()));
    assertTrue(userRepository.save(user));
    assertTrue(employeeActivityRepository.save(activity));
    assertTrue(employeeActivityRepository.deleteById(13L));
  }

  @Test
  void deleteAll() {
    int nrActivities = 10;
    User user = generateUserForTest(0L);
    assertTrue(userRepository.save(user));
    for(int i = 1; i <= nrActivities; i++) {
      Right activity = new Right((long) i, getRandomEmployeeRight());
      EmployeeActivity employeeActivity = generateEmployeeActivityForTest((long) i, user, activity);
      assertTrue(employeeActivityRepository.save(employeeActivity));
    }

    employeeActivityRepository.deleteAll();
    final List<EmployeeActivity> allActivities = employeeActivityRepository.findAll();
    assertTrue(allActivities.isEmpty());
  }
}