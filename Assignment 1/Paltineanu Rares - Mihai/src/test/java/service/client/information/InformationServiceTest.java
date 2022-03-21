package service.client.information;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientService;
import service.client.information.InformationServiceMySQL;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class InformationServiceTest {

    private ClientService<Client, Long> clientService;

    @BeforeEach
    public void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientService = new InformationServiceMySQL(new ClientRepositoryMySQL(connection));
        clientService.removeAll();
    }

    @Test
    public void findAll() {
        assertEquals(0, clientService.findall().size());
    }

    @Test
    public void findById() {
        Long id = 2L;
        assertEquals(null, clientService.findById(id));

        Client client = new ClientBuilder()
                .setName("client")
                .setCNP("1234567890123")
                .setAddress("Address")
                .build();
        clientService.save(client);

        assertNotNull(clientService.findById(client.getId()));

        assertEquals(null, clientService.findById(client.getId() + 1));
    }
}
