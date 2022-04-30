import database.DatabaseConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;

import java.sql.Connection;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountServiceImplTest {
    private static AccountRepository accountRepository;
    private static AccountService accountService;

    private static ClientRepository clientRepository;
    private static ClientService clientService;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection);

        clientService = new ClientServiceImpl(clientRepository);
        accountService = new AccountServiceImpl(accountRepository);
    }

    @BeforeEach
    public void cleanUp() {
        accountRepository.removeAll();
        clientRepository.removeAll();
    }

    @Test
    public void createAccount() throws Exception {
        Client client = new ClientBuilder()
                .setName("John Doe")
                .setIdentityCardNumber("123456")
                .setPersonalNumericalCode("1234567890123")
                .setAddress("Baritiu street")
                .build();

        clientService.save(client);

        Long idClient = clientService.findAll().get(0).getId();

        long millis = System.currentTimeMillis();
        Account account1 = new AccountBuilder()
                .setType("savings")
                .setBalance(2000.0)
                .setDate(new Date(millis))
                .setIdClient(idClient)
                .build();

        Account account2 = new AccountBuilder()
                .setType("current")
                .setBalance(3000.0)
                .setDate(new Date(millis))
                .setIdClient(idClient)
                .build();

        accountService.save(account1);
        accountService.save(account2);

        assertTrue(accountService.findAll().size() == 2);
    }

    @Test
    public void updateAccount() throws Exception {
        Client client = new ClientBuilder()
                .setName("John Doe")
                .setIdentityCardNumber("123456")
                .setPersonalNumericalCode("1234567890123")
                .setAddress("Baritiu street")
                .build();

        clientService.save(client);

        Long idClient = clientService.findAll().get(0).getId();

        long millis = System.currentTimeMillis();
        Account account = new AccountBuilder()
                .setType("savings")
                .setBalance(2000.0)
                .setDate(new Date(millis))
                .setIdClient(idClient)
                .build();

        accountService.save(account);

        Account accountUpdated = new AccountBuilder()
                .setId(accountService.findAll().get(0).getId())
                .setType("savings")
                .setBalance(5000.0)
                .setDate(new Date(millis))
                .setIdClient(idClient)
                .build();

        accountService.updateAccount(accountUpdated);

        assertTrue(accountService.viewAccount(accountUpdated.getId()).getResult().getBalance() == 5000.0);
    }

    @Test
    public void deleteAccount() throws Exception {
        Client client = new ClientBuilder()
                .setName("John Doe")
                .setIdentityCardNumber("123456")
                .setPersonalNumericalCode("1234567890123")
                .setAddress("Baritiu street")
                .build();

        clientService.save(client);

        Long idClient = clientService.findAll().get(0).getId();

        long millis = System.currentTimeMillis();
        Account account1 = new AccountBuilder()
                .setType("savings")
                .setBalance(2000.0)
                .setDate(new Date(millis))
                .setIdClient(idClient)
                .build();

        Account account2 = new AccountBuilder()
                .setType("current")
                .setBalance(3000.0)
                .setDate(new Date(millis))
                .setIdClient(idClient)
                .build();

        accountService.save(account1);
        accountService.save(account2);

        Account accountDeleted = new AccountBuilder()
                .setId(accountService.findAccountsByClientId(idClient).get(0).getId())
                .build();

        accountService.deleteAccount(accountDeleted);

        assertTrue(accountService.findAll().size() == 1);
    }

    @Test
    public void transferBetweenAccounts() throws Exception {
        Client client = new ClientBuilder()
                .setName("John Doe")
                .setIdentityCardNumber("123456")
                .setPersonalNumericalCode("1234567890123")
                .setAddress("Baritiu street")
                .build();

        clientService.save(client);

        Long idClient = clientService.findAll().get(0).getId();

        long millis = System.currentTimeMillis();
        Account account1 = new AccountBuilder()
                .setType("savings")
                .setBalance(2000.0)
                .setDate(new Date(millis))
                .setIdClient(idClient)
                .build();

        Account account2 = new AccountBuilder()
                .setType("current")
                .setBalance(3000.0)
                .setDate(new Date(millis))
                .setIdClient(idClient)
                .build();

        accountService.save(account1);
        accountService.save(account2);

        Long id1 = accountService.findAll().get(0).getId();
        Long id2 = accountService.findAll().get(1).getId();

        accountService.transferBetweenAccounts(id1,id2,500.0);

        assertTrue(accountService.viewAccount(id1).getResult().getBalance() == 1500.0 &&
                accountService.viewAccount(id2).getResult().getBalance() == 3500.0);
    }

    @Test
    public void payBill() throws Exception {
        Client client = new ClientBuilder()
                .setName("John Doe")
                .setIdentityCardNumber("123456")
                .setPersonalNumericalCode("1234567890123")
                .setAddress("Baritiu street")
                .build();

        clientService.save(client);

        Long idClient = clientService.findAll().get(0).getId();

        long millis = System.currentTimeMillis();
        Account account = new AccountBuilder()
                .setType("savings")
                .setBalance(2000.0)
                .setDate(new Date(millis))
                .setIdClient(idClient)
                .build();

        accountService.save(account);

        Long id = accountService.findAll().get(0).getId();

        accountService.payBill(id,1000.0);

        assertTrue(accountService.viewAccount(id).getResult().getBalance() == 1000.0);
    }
}
