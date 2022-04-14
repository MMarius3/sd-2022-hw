package service.user;

import com.sun.glass.ui.Accessible;
import database.DBConnectionFactory;
import model.Account;
import model.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.action.ActionRepository;
import repository.action.ActionRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ActionServiceMySQLTest {
    private static ActionRepository actionRepository;
    private static ActionService actionService;
    private static ClientRepository clientRepository;
    private static AccountRepository accountRepository;

    @BeforeAll
    public static void setUp(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        actionRepository = new ActionRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection);
        actionService = new ActionServiceMySQL(connection,actionRepository);
        accountRepository = new AccountRepositoryMySQL(connection);
    }

    @BeforeEach
    public void cleanUp(){
        actionRepository.removeAll();
        clientRepository.removeAll();
    }

    @Test
    void createClient() {
        assertTrue(actionService.createClient("Test Client","Test id","Test cnp","Test address"));

    }

    @Test
    void updateClient() {
        actionService.createClient("Test Client","Test id","Test cnp","Test address");
        Client client = actionService.findByName("Test Client");
        assertTrue(actionService.updateClient("name",client,"New Test",null,null,null));

    }

    @Test
    void deleteClient() {
        actionService.createClient("Test Client","Test id","Test cnp","Test address");
        Client client = actionService.findByName("Test Client");
        actionService.deleteClient(client);
        assertFalse(
                actionService.findByName(client.getName()) != null
        );
    }

    @Test
    void createAccount() throws Exception{
        assertTrue(actionService.createAccount("Test id","Test type",1000l, Date.from(Instant.now())));
    }

    @Test
    void updateAccount() throws Exception{
        actionService.createAccount("Test id","Test type",1000l, Date.from(Instant.now()));
        Account account = accountRepository.findByClient("Test id");
        assertTrue(
                actionService.updateAccount("type",account,null,"TEST",null)
        );
    }


    @Test
    void deleteAccount()throws Exception {
        actionService.createAccount("Test id","Test type",1000l, Date.from(Instant.now()));
        Account account = accountRepository.findByClient("Test id");
        actionService.deleteAccount(account);
        assertFalse(
                accountRepository.findByClient("Test id") != null
        );
    }

}