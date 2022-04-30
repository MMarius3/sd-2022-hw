package repository_layer.repository.action;

import bussiness_layer.builder.ActionBuilder;
import bussiness_layer.models.Action;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository_layer.database_builder.DBConnectionFactory;
import repository_layer.repository.user_role.UserRoleRepository;
import repository_layer.repository.user_role.UserRoleRepositoryMySQL;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActionRepositoryMySQLTest {

    private static ActionRepository actionRepository;
    private static UserRoleRepository userRoleRepository;

    @BeforeAll
    public static void setupClass() {

        userRoleRepository = new UserRoleRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection());
        actionRepository = new ActionRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection(),userRoleRepository);
    }

    @Test
    void insert()
    {
        Action action = new ActionBuilder()
                .setType("View accounts")
                .setDate(new Date(System.currentTimeMillis()))
                .build();
        actionRepository.insert(action,"EMPLOYEE_USER@yahoo.com");
    }

    @Test
    void retrieveAllByDate() {
        List<Action> actions = actionRepository.retrieveAllByDate(new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()),"EMPLOYEE_USER@yahoo.com");
        assertEquals(actions.size(), 1);
    }
}