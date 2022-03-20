package repository;

import database.DBConnectionFactory;
import model.Account;
import model.builder.AccountBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountRepositoryMySQLTest {
    private static AccountRepository accountRepository;

    @BeforeAll
    public static void setupClass() {
        accountRepository = new AccountRepositoryMySQL(
                new DBConnectionFactory().getConnectionWrapper(true).getConnection());
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
        int year = 2021;
        int month = 3;
        int day = 21;
        Account account1 = new AccountBuilder()
                .setType("Type")
                .setAmount(0.0F)
                .setDateOfCreation(new Date(year-1900,month-1,day))
                .build();
        accountRepository.save(account1);
        accountRepository.save(account1);
        accountRepository.save(account1);

        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 3);
    }

    @Test
    public void findByID() {

    }

    @Test
    public void updateType() {
        Account account1 = new AccountBuilder()
                .setType("Type")
                .setAmount(0.0F)
                .setDateOfCreation(new Date(2021-1900,3-1,15))
                .build();
        accountRepository.save(account1);

        assertTrue(accountRepository.updateType(account1, "Type2"));
    }

    @Test
    public void updateAmount() {
        Account account1 = new AccountBuilder()
                .setType("Type")
                .setAmount(0.0F)
                .setDateOfCreation(new Date(2021-1900,3-1,15))
                .build();
        accountRepository.save(account1);

        assertTrue(accountRepository.updateAmount(account1, 100.0F));
    }

    @Test
    public void updateDateOfCreation() {
        Account account1 = new AccountBuilder()
                .setType("Type")
                .setAmount(0.0F)
                .setDateOfCreation(new Date(2021-1900,3-1,15))
                .build();
        accountRepository.save(account1);

        assertTrue(accountRepository.updateDate(account1, new Date(2018-1900, 1-1, 5)));
    }

    @Test
    public void delete() {
        Account account1 = new AccountBuilder()
                .setType("Type1")
                .setAmount(10.0F)
                .setDateOfCreation(new Date(2021-1900,3-1,15))
                .build();
        accountRepository.save(account1);
        Account account2 = new AccountBuilder()
                .setType("Type2")
                .setAmount(20.0F)
                .setDateOfCreation(new Date(2020-1900,3-1,15))
                .build();
        accountRepository.save(account2);
        Account account3 = new AccountBuilder()
                .setType("Type3")
                .setAmount(30.0F)
                .setDateOfCreation(new Date(2019-1900,3-1,15))
                .build();
        accountRepository.save(account3);

        assertTrue(accountRepository.delete(account1));
        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 2);
    }

    @Test
    public void save() throws Exception {
        assertNotEquals(-1L, accountRepository.save(
                new AccountBuilder()
                        .setType("Type")
                        .setAmount(2000.0F)
                        .setDateOfCreation(new Date(2020-1900, 6-1, 15))
                        .build()
        ));
    }

    @Test
    public void removeAll() throws Exception {

    }
}
