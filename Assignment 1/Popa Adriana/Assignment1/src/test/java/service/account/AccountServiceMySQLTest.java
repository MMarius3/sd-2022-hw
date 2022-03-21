package service.account;

import database.DatabaseConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceMySQLTest {

    private static AccountService accountService;
    private static ClientService clientService;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(true).getConnection();
        AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
        accountService = new AccountServiceMySQL(accountRepository);

        ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        clientService = new ClientServiceMySQL(clientRepository);
    }

    @Test
    void viewAccount() {
        Account account = new AccountBuilder()
                .setClientId(1)
                .setIdentificationNumber("1234 1234 1234 1234")
                .setType("Visa")
                .setAmountOfMoney("500")
                .setDateOfCreation("2022-01-01")
                .build();
        accountService.createAccount(account);
        Optional<Account> account1 = accountService.viewAccount(25);
        assertTrue(account1.isPresent());
    }

    @Test
    void createAccount() {
        Account account = new AccountBuilder()
                .setClientId(2)
                .setIdentificationNumber("1234 1234 1234 1234")
                .setType("Visa")
                .setAmountOfMoney("500")
                .setDateOfCreation("2022-01-01")
                .build();
        boolean created = accountService.createAccount(account);
        assertTrue(created);
    }

    @Test
    void updateAccount() {
        Account account = new AccountBuilder()
                .setClientId(2)
                .setIdentificationNumber("1234 1234 1234 1234")
                .setType("Visa")
                .setAmountOfMoney("500")
                .setDateOfCreation("2022-01-01")
                .build();
        accountService.createAccount(account);
        account.setClientId(3);
        account.setId(1);
        boolean updated = accountService.updateAccount(account);
        assertTrue(updated);
    }

    @Test
    void deleteAccount() {
        Account account = new AccountBuilder()
                .setClientId(2)
                .setIdentificationNumber("1234 1234 1234 1234")
                .setType("Visa")
                .setAmountOfMoney("533")
                .setDateOfCreation("2022-01-01")
                .build();
        accountService.createAccount(account);
        accountService.deleteAccount(1);
        Optional<Account> account1 = accountService.viewAccount(1);
        assertTrue(account1.isEmpty());
    }

    @Test
    void findByClientID() {
        Account account = new AccountBuilder()
                .setClientId(1)
                .setIdentificationNumber("1234 1234 1234 1234")
                .setType("Visa")
                .setAmountOfMoney("533")
                .setDateOfCreation("2022-01-01")
                .build();
        accountService.createAccount(account);
        Optional<Account> account1 = accountService.findByClientID(1);
        assertTrue(account1.isPresent());
    }

    @Test
    void viewAccounts() {
        Account account = new AccountBuilder()
                .setClientId(2)
                .setIdentificationNumber("1234 1234 1234 1234")
                .setType("Visa")
                .setAmountOfMoney("533")
                .setDateOfCreation("2022-01-01")
                .build();
        accountService.createAccount(account);
        List<Account> accounts = accountService.viewAccounts();
        assertEquals(29,accounts.size());
    }
}