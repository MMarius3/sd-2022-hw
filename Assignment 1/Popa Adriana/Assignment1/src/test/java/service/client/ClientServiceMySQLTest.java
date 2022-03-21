package service.client;

import database.DatabaseConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceMySQLTest {

    private static ClientService clientService;

    @BeforeAll
    public static void setUp(){
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(true).getConnection();
        ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        clientService = new ClientServiceMySQL(clientRepository);
    }

    @BeforeEach
    public void cleanUp(){
        clientService.removeAll();
    }

    @Test
    void addClient() {
        Client client = new ClientBuilder()
                .setName("Ana")
                .setIdentityCardNumber("123456")
                .setPersonalNumericCode("23456789")
                .setAddress("Vestem")
                .build();
        assertTrue(clientService.addClient(client));
    }

    @Test
    void viewClients() {
        Client client = new ClientBuilder()
                .setName("Ana")
                .setIdentityCardNumber("123456")
                .setPersonalNumericCode("23456789")
                .setAddress("Vestem")
                .build();
        assertTrue(clientService.addClient(client));
        List<Client> clientList = clientService.viewClients();
        assertEquals(1,clientList.size());
    }

    @Test
    void findClient() {
        Client client = new ClientBuilder()
                .setName("Ana")
                .setIdentityCardNumber("123456")
                .setPersonalNumericCode("23456789")
                .setAddress("Vestem")
                .build();
        assertTrue(clientService.addClient(client));
        Optional<Client> foundClient = clientService.findClient(1);
        assertTrue(foundClient.isEmpty());
    }

    @Test
    void updateClient() {
        Client client = new ClientBuilder()
                .setName("Ana")
                .setIdentityCardNumber("123456")
                .setPersonalNumericCode("23456789")
                .setAddress("Vestem")
                .build();
        assertTrue(clientService.addClient(client));
        client.setId(1);
        client.setName("Alina");
        assertTrue(clientService.updateClient(client));
    }

    @Test
    void removeAll() {
        Client client = new ClientBuilder()
                .setName("Ana")
                .setIdentityCardNumber("123456")
                .setPersonalNumericCode("23456789")
                .setAddress("Vestem")
                .build();
        assertTrue(clientService.addClient(client));
        clientService.removeAll();
        List<Client> clientList = clientService.viewClients();
        assertEquals(0,clientList.size());
    }
}