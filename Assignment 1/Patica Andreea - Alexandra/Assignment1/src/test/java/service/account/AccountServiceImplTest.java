package service.account;

import database.DBConnectionFactory;
import model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepositoryMySQL;
import repository.type.AccountTypeRepositoryMySQL;

import java.sql.Connection;

import static org.junit.Assert.*;

public class AccountServiceImplTest {
    private AccountService accountService;

    @BeforeEach
    public void setup() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountService = new AccountServiceImpl(new AccountRepositoryMySQL(new AccountTypeRepositoryMySQL(connection), connection));
    }

    @Test
    public void findAll() throws Exception {
        assertEquals(0, accountService.findByClientId(Long.parseLong("1")).size());
    }

    @Test
    public void save() throws Exception {
        assertTrue(accountService.save(new Account()));
    }
}
