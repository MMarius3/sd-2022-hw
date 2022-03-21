package service.client;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepositoryMySQL;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceImplTest {
    private ClientService clientService;

    @BeforeEach
    public void setup() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientService = new ClientServiceImpl(new ClientRepositoryMySQL(connection));
        clientService.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        assertEquals(0, clientService.findAll().size());
    }

    @Test
    public void findById() throws Exception {
        Long id = 1L;
        assertThrows(IllegalArgumentException.class, () -> clientService.findById(id));
    }

    @Test
    public void save() throws Exception {
        assertTrue(clientService.save(new ClientBuilder().setFullName("test").setIdNumber("124").setCNP("12451").setAddress("asd").build()));
    }

    @Test
    public void successfulFindById() throws Exception {
        clientService.save(new ClientBuilder().setFullName("test").setIdNumber("124").setCNP("12451").setAddress("asd").build());
        List<Client> clients = clientService.findAll();
        Client client = clientService.findById(clients.get(clients.size()-1).getId());
        assertEquals("test", client.getFullName());
    }

    @Test
    public void updateById() throws Exception {
        clientService.save(new ClientBuilder().setFullName("test").setIdNumber("124").setCNP("12451").setAddress("asd").build());
        List<Client> clients = clientService.findAll();
        Client lastClient = clients.get(clients.size()-1);
        lastClient.setFullName("test2");
        assertTrue(clientService.updateById(lastClient.getId(), lastClient));
    }
}
