package repository;

import database.DataBaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.Account;
import model.builder.AccountBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.Account.AccountRepository;
import repository.Account.AccountRepositoryMySQL;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

 public class AccountRepositoryMySQLTest {

    private static AccountRepository repository;

    @BeforeAll
   public static void setUp() {
        //daca vreau sa folosesc baza de date test_OrderDB
        JDBConnectionWrapper connectionWrapper = DataBaseConnectionFactory.getConnectionWrapper(true);

        //daca vreau sa folosesc baza de date orderDB
        //connectionWrapper = DataBaseConnectionFactory.getConnectionWrapper(false);

        repository = new AccountRepositoryMySQL(connectionWrapper.getConnection());
    }

    @BeforeEach
    public void setup(){
        repository.removeAll();
    }

    @Test
    void findAll() {

       final List<Account> allAccounts = repository.findAll();
       assertTrue(allAccounts.isEmpty());

    }

    @Test
    void findById() throws EntityNotFoundException {

        final List<Account> allAccounts = repository.findAll();
        Long current = allAccounts.get(allAccounts.size()-1).getId();

        Account account = new AccountBuilder().setType("Savings").
                                                setMoneyAmount(4300).
                                                setCreationDate(LocalDate.now()).
                                                setIdentificationNR(19981123).
                                                build();
        repository.save(account);

        assertNotNull(repository.findById(current + 1));

    }

    @Test
    void findByClientId() {
    }

    @Test
    void save() {

        final Account testAccount = new AccountBuilder().setType("Savings").
                                                        setMoneyAmount(450).
                                                        setCreationDate(LocalDate.now()).
                                                        setIdentificationNR(22223333).
                                                        build();

        assertTrue(repository.save(testAccount));

    }

    @Test
    void removeAll() {

        final Account testAccount = new AccountBuilder().setType("Savings").
                                                         setMoneyAmount(450).
                                                         setCreationDate(LocalDate.now()).
                                                         setIdentificationNR(22223333).
                                                         build();
        repository.removeAll();
        final List<Account> allAccounts = repository.findAll();
        assertTrue(allAccounts.isEmpty());

    }

    @Test
    void remove() {

        final Account account = new AccountBuilder().setType("Savings").
                                                setMoneyAmount(3200).
                                                setCreationDate(LocalDate.now()).
                                                setIdentificationNR(11112222).
                                                build();
        repository.save(account);
        assertTrue(repository.remove(account.getId()));

    }

    @Test
    void update() {

        Account account = new AccountBuilder().setType("Savings").
                                            setMoneyAmount(3400).
                                            setCreationDate(LocalDate.now()).
                                            setIdentificationNR(11112222).
                                            build();
        account.setType("Current");
        assertTrue( repository.update(account));

    }

    @Test
    void transfer() throws EntityNotFoundException {

        Account account1 = new AccountBuilder().setType("Savings").
                                            setMoneyAmount(6600).
                                            setCreationDate(LocalDate.now()).
                                            setIdentificationNR(11112222).
                                            build();
        repository.save(account1);

        Account account2 = new AccountBuilder().setType("Savings").
                                            setMoneyAmount(2000).
                                            setCreationDate(LocalDate.now()).
                                            setIdentificationNR(11112222).
                                            build();
        repository.save(account2);

        repository.transfer(account1,account2,1000);
        assertTrue(account1.getMoneyAmount() == 5600);

    }
}