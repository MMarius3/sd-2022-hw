package repository;

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

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientAccountRepositoryMySQLTest {
    private static ClientAccountRepository clientAccountRepository;
    private static ClientRepository clientRepository;
    private static AccountRepository accountRepository;

    @BeforeAll
    public static void setupClass() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientAccountRepository = new ClientAccountRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection);
        accountRepository = new AccountRepositoryMySQL(connection);
    }

    @BeforeEach
    public void cleanUp() {
        clientAccountRepository.removeAll();
        clientRepository.removeAll();
        accountRepository.removeAll();
    }

    @Test
    public void addAccountsToClient() {
        Client client = new ClientBuilder()
                .setName("client")
                .setIdCardNo("MS222222")
                .setCNP("8457956412547")
                .setAddress("address")
                .build();
        Long clientId = clientRepository.save(client);
        client.setId(clientId);

        Account account1 = new AccountBuilder()
                .setType("Zero")
                .setAmount(0.0F)
                .setDateOfCreation(new Date(2018 - 1900, 5 - 1, 5))
                .build();
        account1.setId(accountRepository.save(account1));
        Account account2 = new AccountBuilder()
                .setType("Deluxe")
                .setAmount(5000.0F)
                .setDateOfCreation(new Date(2015 - 1900, 1 - 1, 1))
                .build();
        account2.setId(accountRepository.save(account2));
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        clientAccountRepository.addAccountsToClient(client, accounts);

        List<Account> foundAccounts;
        foundAccounts = clientAccountRepository.findAccountsForClient(clientId);

        assertEquals(foundAccounts.size(), accounts.size());
        for (int i = 0; i < accounts.size(); i++) {
            assertEquals(foundAccounts.get(i).getId(), accounts.get(i).getId());
            assertEquals(foundAccounts.get(i).getType(), accounts.get(i).getType());
            assertEquals(foundAccounts.get(i).getAmount(), accounts.get(i).getAmount());
            assertEquals(foundAccounts.get(i).getDateOfCreation(), accounts.get(i).getDateOfCreation());
        }
    }

}
