package service.Account;

import database.DataBaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.Account;
import model.builder.AccountBuilder;
import model.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.Account.AccountRepositoryMySQL;
import repository.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {
    private static AccountServiceImpl service;

    @BeforeEach
    public void setUp() {

        JDBConnectionWrapper connectionWrapper = DataBaseConnectionFactory.getConnectionWrapper(true);

        service = new AccountServiceImpl(new AccountRepositoryMySQL(connectionWrapper.getConnection()));

    }

    @Test
    void findAll() {

        List<Account> allAccounts = service.findAll();
        assertTrue(allAccounts.isEmpty());

    }

    @Test
    void findById() throws EntityNotFoundException {

    }

    @Test
    void findByClientId() {
    }

    @Test
    void save() {

        final Account testAccount = new AccountBuilder().setType("Savings")
                                                        .setMoneyAmount(450)
                                                        .setCreationDate(LocalDate.now())
                                                        .setIdentificationNR(22223333)
                                                        .build();
        assertTrue(service.save(testAccount).getFormattedErrors().isEmpty());
    }

    @Test
    void update() {

        Account account = new AccountBuilder().setType("Savings").
                setMoneyAmount(3400).
                setCreationDate(LocalDate.now()).
                setIdentificationNR(11112222).
                build();
        account.setType("Current");
        assertTrue(service.update(account).getFormattedErrors().isEmpty());

    }

    @Test
    void removeAll() {

        final Account testAccount = new AccountBuilder().setType("Savings").
                setMoneyAmount(450).
                setCreationDate(LocalDate.now()).
                setIdentificationNR(22223333).
                build();
        service.save(testAccount);
        service.removeAll();
        final List<Account> allAccounts = service.findAll();
        assertTrue(allAccounts.isEmpty());

    }

    @Test
    void remove() {

        final Account account = new AccountBuilder().setType("Savings").
                setMoneyAmount(3200).
                setCreationDate(LocalDate.now()).
                setIdentificationNR(11112222).
                build();
        service.update(account);
        assertTrue(service.remove(account.getId()));

    }

    @Test
    void transfer() throws EntityNotFoundException {

        Account account1 = new AccountBuilder().setType("Savings").
                setMoneyAmount(6600).
                setCreationDate(LocalDate.now()).
                setIdentificationNR(11112222).
                build();
        service.save(account1);

        Account account2 = new AccountBuilder().setType("Savings").
                setMoneyAmount(2000).
                setCreationDate(LocalDate.now()).
                setIdentificationNR(11112222).
                build();
        service.save(account2);

        service.transfer(account1,account2,1000);
        assertTrue(account1.getMoneyAmount() == 5600);

    }
}