import Database.DBConnectionFactory;
import Model.Client;
import Repository.Account.AccountRepository;
import Repository.Account.AccountRepositoryMySQL;
import Repository.Client.ClientRepository;
import Repository.Client.ClientRepositoryMySQL;
import Service.Account.AccountService;
import Service.Account.AccountServiceMySQL;
import Service.Client.ClientService;
import Service.Client.ClientServiceMySQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceMySQLTest {


    private static ClientService clientService;
    private static ClientRepository clientRepository;
    private static AccountService accountService;
    private static AccountRepository accountRepository;

    @BeforeAll
    public static void setUp() throws SQLException {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);
        accountRepository = new AccountRepositoryMySQL(connection);
        clientService = new ClientServiceMySQL(clientRepository);
        accountService = new AccountServiceMySQL(accountRepository);
        connection.isValid(10);
    }

    @BeforeEach
    public void cleanUp() {
        clientRepository.removeAll();
        accountRepository.removeAll();
    }

    @Test
    public void add() throws  Exception{

        String name = "Alex";
        Long cardnr = 301L;
        Long pnc = 12236L;
        String address = "26,Str.Macului";

        accountService.addAccount(cardnr,"savings",2000);
        assertTrue(clientService.addClient(name,cardnr,pnc,address));
        assertNotNull(clientRepository.findByCardNr(cardnr));
    }

    @Test
    public void update() throws Exception {
        String name = "Alex";
        Long cardnr= 300L;
        Long pnc = 12556L;
        String address = "29,Str.Macului";

        String newAddress = "23,Str.Florilor";
        accountService.addAccount(cardnr,"savings",2000);
        clientService.addClient(name,cardnr,pnc,address);

        Client c = clientRepository.findByCardNr(cardnr);
        assertNotEquals(c.getAddress(),newAddress);
        assertTrue(clientService.updateClient(c.getId(),"",-1L,newAddress));
        assertEquals(clientRepository.findById(c.getId()).getAddress(),newAddress);
    }

    @Test
    public void view() throws Exception {
        assertNotNull(clientService.viewClients());
    }
}
