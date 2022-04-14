package repository.client_account;

import database.JDBConnectionWrapper;
import model.Client;
import model.ClientAccount;
import model.builder.ClientAccountBuilder;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import static database.Constants.Schemas.TEST;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientAccountRepositoryTestMySQL {
    private ClientAccountRepository clientAccountRepository;
    private ClientRepository clientRepository;
    @BeforeEach
    public void setup() {
        Connection connection = new JDBConnectionWrapper(TEST).getConnection();
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.clientAccountRepository = new ClientAccountRepositoryMySQL(connection,clientRepository);
    }

    @BeforeEach
    public void cleanUp() {
        clientAccountRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<ClientAccount> clientAccounts = clientAccountRepository.findAll();
        assertEquals(clientAccounts.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Client client = new ClientBuilder()
                .setId(1)
                .setName("Antonia")
                .setAddress("Aici")
                .setIdNumber("ZV444444")
                .setCnp("1230323020202")
                .build();

        ClientAccount clientAccount = new ClientAccountBuilder()
                .setType("savings")
                .setAmount(43.44)
                .setCreationDate(Date.valueOf("2022-10-10"))
                .build();
        clientAccountRepository.add(clientAccount,client);
        clientAccountRepository.add(clientAccount,client);
        clientAccountRepository.add(clientAccount,client);

        List<ClientAccount> clientAccounts = clientAccountRepository.findAll();
        assertEquals(clientAccounts.size(), 3);
    }

    @Test
    public void delete() throws Exception {
        Client client = new ClientBuilder()
                .setId(1)
                .setName("Antonia")
                .setAddress("Aici")
                .setIdNumber("ZV444444")
                .setCnp("1230323020202")
                .build();

        ClientAccount clientAccount = new ClientAccountBuilder()
                .setType("savings")
                .setAmount(43.44)
                .setCreationDate(Date.valueOf("2022-10-10"))
                .setIdentificationNumber(43)
                .build();

        clientAccountRepository.add(clientAccount,client);

        assertTrue(clientAccountRepository.delete(clientAccount));
    }

    @Test
    public void add() throws Exception {
        assertTrue(clientAccountRepository.add(
                new ClientAccountBuilder()
                        .setType("savings")
                        .setAmount(43.44)
                        .setCreationDate(Date.valueOf("2022-10-10"))
                        .build(),
                new ClientBuilder()
                        .setId(1)
                        .setName("Antonia")
                        .setAddress("Aici")
                        .setIdNumber("ZV444444")
                        .setCnp("1230323020202")
                        .build()
        ));
    }

    @Test
    public void removeAll() throws Exception {
        assertTrue(clientAccountRepository.removeAll());
    }
}
