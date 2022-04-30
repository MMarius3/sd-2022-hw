package service;

import database.DBConnectionFactory;
import model.EmployeeActivity;
import model.Right;
import model.User;
import model.builder.EmployeeActivityBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.activity.EmployeeActivityRepository;
import repository.activity.EmployeeActivityRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.activity.EmployeeActivityServiceImpl;

import java.sql.Connection;
import java.util.List;

import static common.TestEntityGenerator.generateEmployeeActivityForTest;
import static common.TestEntityGenerator.generateUserForTest;
import static database.enums.RightType.getRandomEmployeeRight;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeActivityServiceImplTest {

  private static EmployeeActivityServiceImpl employeeActivityService;
  private static UserRepository userRepository;

  @BeforeAll
  public static void setup() {
    Connection connection = DBConnectionFactory.getConnectionWrapper(true).getConnection();
    RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
    userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
    EmployeeActivityRepository employeeActivityRepository = new EmployeeActivityRepositoryMySQL(connection, userRepository, rightsRolesRepository);
    employeeActivityService = new EmployeeActivityServiceImpl(employeeActivityRepository);
  }

  @BeforeEach
  public void cleanup() {
    userRepository.deleteAll();
    employeeActivityService.deleteAll();
  }

  @Test
  void findAll() {
    final List<EmployeeActivity> accounts = employeeActivityService.findAll();
    assertTrue(accounts.isEmpty());

    int nrActivities = 10;
    User user = generateUserForTest(0L);
    assertTrue(userRepository.save(user));
    for(int i = 1; i <= 10; i++) {
      Right activity = new Right((long) i, getRandomEmployeeRight());
      EmployeeActivity employeeActivity = generateEmployeeActivityForTest((long) i, user, activity);
      employeeActivityService.save(employeeActivity);
    }

    final List<EmployeeActivity> allActivities = employeeActivityService.findAll();
    assertEquals(nrActivities, allActivities.size());
  }

  @Test
  void save() {
    final EmployeeActivity employeeActivity = new EmployeeActivityBuilder().build();
    assertFalse(employeeActivityService.save(employeeActivity));

    final User user = generateUserForTest(0L);
    assertTrue(userRepository.save(user));
    final EmployeeActivity noGivenIdEmployeeActivity = generateEmployeeActivityForTest(0L, user, new Right(1L, getRandomEmployeeRight()));
    assertTrue(employeeActivityService.save(noGivenIdEmployeeActivity));

    final EmployeeActivity givenIdActivity = generateEmployeeActivityForTest(13L, user, new Right(2L, getRandomEmployeeRight()));
    assertTrue(employeeActivityService.save(givenIdActivity));
  }

  @Test
  void deleteById() {
    final User user = generateUserForTest(0L);
    final EmployeeActivity activity = generateEmployeeActivityForTest(13L, user, new Right(1L, getRandomEmployeeRight()));
    assertTrue(userRepository.save(user));
    assertTrue(employeeActivityService.save(activity));
    assertTrue(employeeActivityService.deleteById(13L));
  }

  @Test
  void deleteAll() {
    int nrActivities = 10;
    User user = generateUserForTest(0L);
    assertTrue(userRepository.save(user));
    for(int i = 1; i <= nrActivities; i++) {
      Right activity = new Right((long) i, getRandomEmployeeRight());
      EmployeeActivity employeeActivity = generateEmployeeActivityForTest((long) i, user, activity);
      employeeActivityService.save(employeeActivity);
    }

    employeeActivityService.deleteAll();
    final List<EmployeeActivity> allActivities = employeeActivityService.findAll();
    assertTrue(allActivities.isEmpty());
  }
}