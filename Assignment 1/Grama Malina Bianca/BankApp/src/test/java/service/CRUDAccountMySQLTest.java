package service;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientAccountRepository;
import repository.client.ClientAccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import service.account.AccountUpdateFields;
import service.account.CRUDAccount;
import service.account.CRUDAccountMySQL;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CRUDAccountMySQLTest {
    private static CRUDAccount crudAccount;
    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;
    private static ClientAccountRepository clientAccountRepository;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection);
        clientAccountRepository = new ClientAccountRepositoryMySQL(connection);
        crudAccount = new CRUDAccountMySQL(clientRepository, accountRepository, clientAccountRepository);
    }

    @BeforeEach
    public void cleanUp() {
        accountRepository.removeAll();
        clientRepository.removeAll();
        clientAccountRepository.removeAll();
    }

    private Account insertAccountZero() {
        Account account1 = new AccountBuilder()
                .setType("Zero")
                .setAmount(0.0F)
                .setDateOfCreation(new Date(2018 - 1900, 5 - 1, 5))
                .build();
        account1.setId(accountRepository.save(account1));
        return account1;
    }

    private Account insertAccountDeluxe(){
        Account account2 = new AccountBuilder()
                .setType("Deluxe")
                .setAmount(5000.0F)
                .setDateOfCreation(new Date(2015 - 1900, 1 - 1, 1))
                .build();
        account2.setId(accountRepository.save(account2));
        return account2;
    }

    private Account insertAccountPremium() {
        Account account3 = new AccountBuilder()
                .setType("Premium")
                .setAmount(2000.0F)
                .setDateOfCreation(new Date(2020 - 1900, 9 - 1, 25))
                .build();
        account3.setId(accountRepository.save(account3));
        return account3;
    }

    @Test
    public void viewAccounts() {
        Account account1 = insertAccountZero();
        Account account2 = insertAccountDeluxe();
        Account account3 = insertAccountPremium();

        List<Account> insertedAccounts = new ArrayList<>();
        insertedAccounts.add(account1);
        insertedAccounts.add(account2);
        insertedAccounts.add(account3);

        List<Account> foundAccounts;
        foundAccounts = crudAccount.viewAccounts();

        assertEquals(insertedAccounts.size(), foundAccounts.size());
        for (int i = 0; i < insertedAccounts.size(); i++) {
            assertEquals(insertedAccounts.get(i).getId(), foundAccounts.get(i).getId());
            assertEquals(insertedAccounts.get(i).getType(), foundAccounts.get(i).getType());
            assertEquals(insertedAccounts.get(i).getAmount(), foundAccounts.get(i).getAmount());
            assertEquals(insertedAccounts.get(i).getDateOfCreation(), foundAccounts.get(i).getDateOfCreation());
        }
    }

    @Test
    public void updateAccount() {
        Account account1 = insertAccountZero();
        insertAccountDeluxe();
        insertAccountPremium();

        account1.setType("Premium");

        assertTrue(crudAccount.updateAccount(account1, account1, AccountUpdateFields.TYPE));
    }

    @Test
    public void openAccount() {
        Client client = new ClientBuilder()
                .setName("name")
                .setIdCardNo("MS123456")
                .setCNP("4578945123587")
                .setAddress("str addr")
                .build();
        Long clientID = clientRepository.save(client);
        client.setId(clientID);
        assertNotEquals(clientID, -1L);

        Account account0 = new AccountBuilder()
                .setType("Zero")
                .setAmount(0.0F)
                .setDateOfCreation(new Date(2018 - 1900, 5 - 1, 5))
                .build();

        assertTrue(crudAccount.openAccount(client, account0));

        List<Account> foundAccounts = clientAccountRepository.findAccountsForClient(client.getId());
        assertEquals(foundAccounts.get(foundAccounts.size()-1).getId(), account0.getId());
        assertEquals(foundAccounts.get(foundAccounts.size()-1).getType(), account0.getType());
        assertEquals(foundAccounts.get(foundAccounts.size()-1).getAmount(), account0.getAmount());
        assertEquals(foundAccounts.get(foundAccounts.size()-1).getDateOfCreation(), account0.getDateOfCreation());
    }

    @Test
    public void transferMoney() {
        Account from = insertAccountDeluxe();
        Account to = insertAccountZero();
        assertTrue(crudAccount.transferMoney(from, to, 1000.0F));
    }

}
