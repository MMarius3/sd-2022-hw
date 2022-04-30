package repositories.account;

import launcher.ComponentFactory;
import model.Account;
import model.builder.AccountBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountRepositoryMySQLTest {

    private static AccountRepository accountRepository;

    @BeforeEach
    void cleanup(){
        accountRepository.removeAll();
    }

    @BeforeAll
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        accountRepository = componentFactory.getAccountRepository();
    }

    @Test
    void findAllByClientId() {
        Account account = new AccountBuilder().setId(1L).setType("debit").setAmount(100L).setClientID(1L).build();
        Account account2 = new AccountBuilder().setId(2L).setType("credit").setAmount(100L).setClientID(1L).build();
        Account account3 = new AccountBuilder().setId(3L).setType("credit").setAmount(20L).setClientID(2L).build();
        accountRepository.save(account);
        accountRepository.save(account2);
        accountRepository.save(account3);
        assertEquals(2,accountRepository.findAllByClientId(1L).size());
    }

    @Test
    void save(){
        assertTrue(accountRepository.save(
                new AccountBuilder()
                        .setId(1L)
                        .setType("debit")
                        .setAmount(100L)
                        .setClientID(1L)
                        .build()
        ));
    }

    @Test
    public void deleteByClientId() throws Exception{
        Account account = new AccountBuilder().setId(1L).setType("debit").setAmount(100L).setClientID(1L).build();
        Account account2 = new AccountBuilder().setId(2L).setType("credit").setAmount(100L).setClientID(1L).build();
        Account account3 = new AccountBuilder().setId(3L).setType("credit").setAmount(20L).setClientID(2L).build();
        accountRepository.save(account);
        accountRepository.save(account2);
        accountRepository.save(account3);
        accountRepository.deleteByClientId(1L);
        assertEquals(1,accountRepository.findAll().size());
    }
}