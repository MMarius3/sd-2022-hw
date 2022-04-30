package service.client;

import database.DBConnectionFactory;
import model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMock;
import repository.client.ClientRepositoryMySQL;
import repository.client_account.ClientAccountRepositoryMySQL;
import service.account.AccountServiceImpl;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceImplTest {

    private ClientService clientService;

    @BeforeEach
    public void setup() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientService = new ClientServiceImpl(new ClientRepositoryMySQL(connection));
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
    public void addClient() throws Exception {
        String name = "Marius";
        Integer idCardNumber = 12345;
        Long idCode = 5082000L;
        assertTrue(clientService.addClient(name, idCardNumber, idCode));
    }

    @Test
    public void updateClient() throws Exception {
        Long id = 1L;
        String name = "Mariusito";
        Integer idCardNumber = 12345;
        Long idCode = 5082000L;
        assertTrue(clientService.updateClient(id, name, idCardNumber, idCode));
    }

    @Test
    public void viewClient() throws Exception {
        Long id = 2L;
        assertTrue(clientService.viewClient(id) != null);
    }
}