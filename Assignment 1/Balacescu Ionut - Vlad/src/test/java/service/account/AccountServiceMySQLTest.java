package service.account;

import database.DBConnectionFactory;
import model.builder.AccountBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountServiceMySQLTest {
    private static AccountService accountService;
    private static AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();

        accountRepository = new AccountRepositoryMySQL(connection);

        accountService = new AccountServiceMySQL(accountRepository);
    }

    @Test
    void findAll() {
    }

    @Test
    void addAccount() {
        assertTrue(accountService.addAccount(new AccountBuilder().setTypeAccount("ddd").setCreationDate("ddd").build(),1));
    }

    @Test
    void updateAccount() {
    }

    @Test
    void getAccountsForClient() {
    }

    @Test
    void findAccountById() {
    }

    @Test
    void deleteAccount() {
    }
}