import database.DatabaseConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceImpl;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientServiceImplTest {
    private static ClientRepository clientRepository;
    private static ClientService clientService;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);

        clientService = new ClientServiceImpl(clientRepository);
    }

    @BeforeEach
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    public void createClient() throws Exception {
        Client client = new ClientBuilder()
                .setName("John Doe")
                .setIdentityCardNumber("123456")
                .setPersonalNumericalCode("1234567890123")
                .setAddress("Baritiu street")
                .build();

        clientService.save(client);
        assertTrue(clientService.findAll().size() == 1);
    }

    @Test
    public void updateClient() throws Exception {
        Client client = new ClientBuilder()
                .setName("John Doe")
                .setIdentityCardNumber("123456")
                .setPersonalNumericalCode("1234567890123")
                .setAddress("Baritiu street")
                .build();

        clientService.save(client);

        Client clientUpdated = new ClientBuilder()
                .setId(clientService.findAll().get(0).getId())
                .setName("John Popescu")
                .setIdentityCardNumber("123456")
                .setPersonalNumericalCode("1234567890123")
                .setAddress("Baritiu street")
                .build();

        clientService.updateClient(clientUpdated);

        assertTrue(clientService.viewClient(clientUpdated.getId()).getResult().getName().equals("John Popescu"));
    }
}
