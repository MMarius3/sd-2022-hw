package repository.client;

import database.JDBConnectionWrapper;
import model.Client;
import model.builder.ClientBuilder;
import model.validator.ClientValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.client.ClientServiceImpl;

import java.sql.Connection;
import java.util.List;

import static database.Constants.Schemas.TEST;
import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryTestMySQL {

    private ClientRepository clientRepository;
    @BeforeEach
    public void setup() {
        Connection connection = new JDBConnectionWrapper(TEST).getConnection();
        this.clientRepository = new ClientRepositoryMySQL(connection);
    }

    @BeforeEach
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Client client = new ClientBuilder()
                .setName("Antonia")
                .setAddress("Aici")
                .setIdNumber("ZV444444")
                .setCnp("1230323020202")
                .build();
        clientRepository.add(client);
        clientRepository.add(client);
        clientRepository.add(client);

        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 3);
    }

    @Test
    public void findById() throws Exception {
        assertNotNull(clientRepository.findById(1));
    }

    @Test
    public void save() throws Exception {
        assertTrue(clientRepository.add(
                new ClientBuilder()
                        .setName("Antonia")
                        .setAddress("Aici")
                        .setIdNumber("ZV444444")
                        .setCnp("1230323020202")
                        .build()
        ));
    }

    @Test
    public void removeAll() throws Exception {
        assertTrue(clientRepository.removeAll());
    }
}
