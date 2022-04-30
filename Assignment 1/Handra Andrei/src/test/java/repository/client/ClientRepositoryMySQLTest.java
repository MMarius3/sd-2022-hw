package repository.client;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.user.UserRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryMySQLTest {

    private static ClientRepository clientRepository;
    private static Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();

    @BeforeAll
    public static void setupClass() {
        clientRepository = new ClientRepositoryMySQL(connection);
    }

    @BeforeEach
    void cleanUp() {
        clientRepository.removeAll();
    }

    @AfterAll
    public static void clean(){
        clientRepository.removeAll();
    }

    @Test
    void findAll() {
        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(),0);
    }

    @Test
    void findById() {
        Client client1 = new ClientBuilder()
                .setName("Andrei")
                .setCardNumber("1234567887654321")
                .setCNP("40000506005605")
                .setAddress("Strada P.")
                .build();
        Client client2 = new ClientBuilder()
                .setName("Andreeei")
                .setCardNumber("1234567857654321")
                .setCNP("40000506115605")
                .setAddress("Strada P.")
                .build();
        clientRepository.save(client1);
        clientRepository.save(client2);
        Optional<Client> client = clientRepository.findById(client1.getId());
        assertFalse(client.isEmpty());
        Optional<Client> noClient = clientRepository.findById(4L);
        assertTrue(noClient.isEmpty());
    }

    @Test
    void save() {
        assertTrue(clientRepository.save(new ClientBuilder()
        .setName("name")
        .setCardNumber("45467890321445678")
        .setCNP("5000498756")
        .setAddress("Strada Petre")
        .build()));
    }

    @Test
    void removeAll() {
        Client client1 = new ClientBuilder()
                .setName("Andrei")
                .setCardNumber("1234567887654321")
                .setCNP("40000506005605")
                .setAddress("Strada P.")
                .build();
        Client client2 = new ClientBuilder()
                .setName("Andrei")
                .setCardNumber("1234567857654321")
                .setCNP("40000506115605")
                .setAddress("Strada P.")
                .build();
        clientRepository.save(client1);
        clientRepository.save(client2);
        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(),2);
        clientRepository.removeAll();
        clients = clientRepository.findAll();
        assertEquals(clients.size(),0);
    }
}