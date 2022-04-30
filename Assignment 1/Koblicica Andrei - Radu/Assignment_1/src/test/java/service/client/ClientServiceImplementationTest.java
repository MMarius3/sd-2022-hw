package service.client;

import database.JDBConnectionWrapper;
import model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepositoryMySQL;

import static database.Constants.Schemas.TEST;
import static org.junit.jupiter.api.Assertions.*;

class ClientServiceImplementationTest {
    private ClientService clientService;

    @BeforeEach
    public void setup() {
        JDBConnectionWrapper connectionWrapper=new JDBConnectionWrapper(TEST);
        clientService = new ClientServiceImplementation(new ClientRepositoryMySQL(connectionWrapper.getConnection()));
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
        assertTrue(clientService.save(new Client()));
    }
}