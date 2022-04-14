package service.account;

import database.Constants;
import database.DBConnectionFactory;
import model.Account;
import model.builder.AccountBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientServiceMySQL;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceMySQLTest {

    private AccountService accountService;

    @BeforeEach
    public void setUp(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountService = new AccountServiceMySQL(new AccountRepositoryMySQL(connection));
    }

    @Test
    void findAll() {
        assertEquals(0, accountService.findAll().size());
    }

    @Test
    void findById() {
        Long id = -1L;
        assertThrows(IllegalArgumentException.class, () -> accountService.findById(id));

    }

    @Test
    void save() {
        Account account = new AccountBuilder()
                .setType(Constants.AccountTypes.CREDIT)
                .setAmount(0.0)
                .setDateOfCreation(Date.valueOf(LocalDate.now()))
                .build();
        assertTrue(accountService.save(account));
    }

}