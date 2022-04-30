package service.client.account;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientService;
import service.client.information.InformationServiceMySQL;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountServiceTest {
    private AccountService accountService;
    private ClientService<Client, Long> clientService;

    @BeforeEach
    public void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientService = new InformationServiceMySQL(new ClientRepositoryMySQL(connection));
        clientService.removeAll();

        accountService = new AccountServiceMySQL(new AccountRepositoryMySQL(connection));
        accountService.removeAll();
    }

    @Test
    public void findAll() {
        assertEquals(0, accountService.findall().size());
    }

    @Test
    public void findById() {
        Long id = 2L;
        assertEquals(null, accountService.findById(id));
    }

    @Test
    public void add() {
        Client client = new ClientBuilder()
                .setName("Rares")
                .setAddress("IDK")
                .setCNP("9999999999999")
                .build();
        clientService.save(client);

        Account account = Account.builder()
                .client_id(client.getId())
                .money(1234)
                .type("maestro")
                .number("1234123412341234")
                .date(Date.valueOf(LocalDate.now()))
                .build();
        accountService.save(account);

        assertNotNull(clientService.findById(client.getId()));
        assertNotNull(accountService.findById(account.getId()));
    }

}
