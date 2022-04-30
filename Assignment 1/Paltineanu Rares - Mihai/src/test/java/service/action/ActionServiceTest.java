package service.action;

import database.DBConnectionFactory;
import model.Action;
import model.User;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.action.ActionRepository;
import repository.action.ActionRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.UserService;
import service.user.UserServiceMySQL;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Actions.*;
import static org.junit.Assert.assertNotNull;

public class ActionServiceTest {

    private static ActionService actionService;
    private static UserService userService;
    private static ActionRepository actionRepository;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightRolesRepository = new RightsRolesRepositoryMySQL(connection);

        UserRepository userRepository = new UserRepositoryMySQL(connection, rightRolesRepository);

        userService = new UserServiceMySQL(userRepository, rightRolesRepository);
        actionRepository = new ActionRepositoryMySQL(connection);
        actionService = new ActionServiceMySQL(actionRepository);
    }

    @BeforeEach
    public void cleanUp() {
        userService.removeAll();
    }

    @Test
    public void addAction() {
        User user = new UserBuilder()
                .setUsername("testt")
                .setPassword("test12!")
                .setRoles(new ArrayList<>())
                .build();

        userService.save(user);

        Action action = Action.builder()
                .user_id(user.getId())
                .action(DELETE_ACCOUNT)
                .date(Date.valueOf(LocalDate.now()))
                .build();

        actionService.save(action);
        assertNotNull(actionService.filterByDateAndId(user.getId(), action.getDate(), action.getDate()));
    }
}
