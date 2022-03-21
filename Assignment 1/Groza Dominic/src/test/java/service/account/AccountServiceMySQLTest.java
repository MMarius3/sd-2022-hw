package service.account;

import database.DBConnectionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountServiceMySQLTest {

    private static AccountService accountService;
    private static AccountRepository accountRepository;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();

        accountRepository = new AccountRepositoryMySQL(connection);
        accountService=new AccountServiceMySQL(accountRepository);

    }

    @BeforeEach
    public void cleanUp() {
        accountRepository.removeAll();
    }


    @Test
    public void register() throws Exception {
        assertTrue(
                accountService.register(1L, 400L,"trading_account")
        );
    }



}