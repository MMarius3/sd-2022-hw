package service.account;

import com.mysql.cj.jdbc.ConnectionWrapper;
import database.JDBConnectionWrapper;
import model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepositoryMySQL;

import java.sql.SQLException;

import static database.Constants.Schemas.TEST;
import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplementationTest {

    private AccountService accountService;

    @BeforeEach
    public void setup() {
        JDBConnectionWrapper connectionWrapper=new JDBConnectionWrapper(TEST);
        accountService = new AccountServiceImplementation(new AccountRepositoryMySQL(connectionWrapper.getConnection()));
    }

    @Test
    public void findAll() throws Exception {
        assertEquals(0, accountService.findAll().size());
    }

    @Test
    public void findById() throws Exception {
        Long id = 1L;
        assertThrows(IllegalArgumentException.class, () -> accountService.findById(id));
    }

    @Test
    public void save() throws Exception {
        assertTrue(accountService.save(new Account()));
    }

    @Test
    public void remove() throws Exception{
        Account account=new Account();
        accountService.save(account);
        assertTrue(accountService.remove(account));
    }
}