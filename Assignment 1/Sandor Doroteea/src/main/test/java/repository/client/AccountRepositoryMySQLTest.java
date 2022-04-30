package repository.client;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountRepositoryMySQLTest {
    private static AccountRepository accountRepository;

    @BeforeAll
    public static void setUp(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);
        accountRepository.removeAll();
    }

    @BeforeEach
    public void cleanUp() {
        accountRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Account account = new Account();
        account.setBalance(1232L);
        account.setType("Savings");
        account.setDateOfCreation(new Date());
        account.setClient_id(13L);
        accountRepository.save(account);

        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 1);
    }
    @Test
    public void update() throws Exception{
        Account account = new Account();
        account.setBalance(1232L);
        account.setType("Savings");
        account.setDateOfCreation(new Date());
        account.setClient_id(13L);
        accountRepository.save(account);
        account.setType("Checking");
        assertTrue(accountRepository.update(account));
    }

    @Test
    public void save() throws Exception {

        Account account = new Account();
        account.setBalance(1232L);
        account.setType("Savings");
        account.setDateOfCreation(new Date());
        account.setClient_id(13L);

        assertTrue(accountRepository.save(account));
    }

    @Test
    public void removeAll() throws Exception {
        accountRepository.removeAll();
        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 0);
    }
}
