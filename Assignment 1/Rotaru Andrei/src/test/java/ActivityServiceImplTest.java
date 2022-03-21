import database.DatabaseConnectionFactory;
import model.Activity;
import model.builder.ActivityBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import service.activity.ActivityService;
import service.activity.ActivityServiceImpl;

import java.sql.Connection;
import java.sql.Date;

import static database.Constants.Roles.EMPLOYEE;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActivityServiceImplTest {
    private static ActivityRepository activityRepository;
    private static ActivityService activityService;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        activityRepository = new ActivityRepositoryMySQL(connection);

        activityService = new ActivityServiceImpl(activityRepository,rightsRolesRepository);
    }

    @BeforeEach
    public void cleanUp() {
        activityRepository.removeAll();
    }

    @Test
    public void filterActivity() throws Exception {
        long millis=System.currentTimeMillis();
        Activity activity1 = new ActivityBuilder()
                .setIdEmployee(2L)
                .setRole(EMPLOYEE)
                .setDate(new Date(millis))
                .setDescription("Added client")
                .build();

        Activity activity2 = new ActivityBuilder()
                .setIdEmployee(2L)
                .setRole(EMPLOYEE)
                .setDate(new Date(millis))
                .setDescription("Deleted client")
                .build();

        activityService.save(activity1);
        activityService.save(activity2);

        assertTrue(activityService.findByDateAndId(2L,"2022-03-20","2022-03-21").size() == 2);
    }
}
