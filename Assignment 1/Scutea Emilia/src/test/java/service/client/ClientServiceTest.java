package service.client;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepositoryMySQL;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {

    private ClientService clientService;

    @BeforeEach
    public void setUp(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientService = new ClientServiceMySQL(new ClientRepositoryMySQL(connection));
    }

    @Test
    public void findAll(){
        assertEquals(0, clientService.findAll().size());
    }

    @Test
    public void findById(){
        Long id = 1L;
        assertThrows(IllegalArgumentException.class, () -> clientService.findById(id));
    }

    @Test
    public void save(){
        Client client = new ClientBuilder()
                .setAddress("Observatorului 34")
                .setCardNumber(1234L)
                .setName("Ana Maria Popescu")
                .setPersonalNumericalCode(6001023324816L)
                .setAccount(1L)
                .build();
        assertTrue(clientService.save(client));
    }
}
