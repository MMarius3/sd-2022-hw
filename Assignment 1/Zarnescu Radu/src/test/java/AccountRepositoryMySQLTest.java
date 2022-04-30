import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import model.Account;
import model.builder.AccountBuilder;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountRepositoryMySQLTest {
    private static AccountRepository repository;

    @BeforeAll
    public static void setupClass(){
        JDBConnectionWrapper connectionWrapper = DBConnectionFactory.getConnectionWrapper(true);
        repository = new AccountRepositoryMySQL(connectionWrapper.getConnection());
    }

    @BeforeEach
    public void setup(){
        repository.removeAll();
    }

    @Test
    public void findAll() throws SQLException {
        List<Account> noAccounts = repository.findAll();
        assertTrue(noAccounts.isEmpty());

        Account account = new AccountBuilder()
                .setType("Personal")
                .setBalance(0.0F)
                .setDateOfCreation(LocalDate.now())
                .setClientID(1L)
                .build();

        Account account2 = new AccountBuilder()
                .setType("Savings")
                .setBalance(0.0F)
                .setDateOfCreation(LocalDate.now())
                .setClientID(1L)
                .build();

        Account account3 = new AccountBuilder()
                .setType("Personal")
                .setBalance(0.0F)
                .setDateOfCreation(LocalDate.now())
                .setClientID(2L)
                .build();

        repository.save(account);
        repository.save(account2);
        repository.save(account3);

        List<Account> accounts = repository.findAll();
        assertEquals(3, accounts.size());
    }

//    @Test
//    public void findById(){
//        User user = new UserBuilder()
//                .setFullName("User")
//                .setCardNumber("5050-1023-2094-4331")
//                .setPnc("2848372")
//                .setAddress("Str. test, nr. 1")
//                .build();
//
//        repository.save(user);
//
//        Optional<User> foundUser = repository.findById(12L);
//
//        User user1 = foundUser.orElse(null);
//
//        assertTrue(user.getId() == user1.getId());
//
//    }

    @Test
    public void save(){
        Account account = new AccountBuilder()
                .setType("Personal")
                .setBalance(0.0F)
                .setDateOfCreation(LocalDate.now())
                .setClientID(1L)
                .build();

        assertTrue(repository.save(account));

        Account accountInvalid = new AccountBuilder()
                .setBalance(0.0F)
                .setDateOfCreation(LocalDate.now())
                .setClientID(1L)
                .build();

        assertFalse(repository.save(accountInvalid));
    }

    @Test
    public void removeAll() throws SQLException {
        Account account = new AccountBuilder()
                .setType("Personal")
                .setBalance(0.0F)
                .setDateOfCreation(LocalDate.now())
                .setClientID(1L)
                .build();
        repository.removeAll();
        List<Account> noAccounts = repository.findAll();
        assertTrue(noAccounts.isEmpty());
    }
}
