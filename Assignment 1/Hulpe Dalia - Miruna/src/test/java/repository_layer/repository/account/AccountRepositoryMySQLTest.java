package repository_layer.repository.account;

import bussiness_layer.builder.AccountBuilder;
import bussiness_layer.models.Account;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository_layer.database_builder.DBConnectionFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountRepositoryMySQLTest {

    private static AccountRepository accountRepository;

    @BeforeAll
    public static void setupClass() {

        accountRepository = new AccountRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection());
    }

    @Test
    void insert() {
        Account account = new AccountBuilder()
                .setType("Test")
                .setAmountOfMoney(110.50f)
                .build();
        accountRepository.insert(account,"CUSTOMER_USER@yahoo.com");
    }

    @Test
    void getUserAccounts() {
        List<Account> accounts = accountRepository.getUserAccounts("CUSTOMER_USER@yahoo.com");
        assertEquals(accounts.size(), 3);
    }

    @Test
    void getById() {
        Account account = accountRepository.getById(2l);
        assertEquals(account.getType(), "Euro");
    }

    @Test
    void update() {
        Account account = new AccountBuilder()
                .setType("Test2")
                .setAmountOfMoney(110.50f)
                .build();
        accountRepository.update(2l,account);
        Account testAccount = accountRepository.getById(2l);
        assertEquals(testAccount.getType(),"Test2");
    }
}