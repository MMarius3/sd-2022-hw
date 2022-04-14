package repository.client;

import database.DBConnectionFactory;
import model.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientRepositoryMySQLTest {
    private static ClientRepository clientRepository;

    @BeforeAll
    public static void setUp(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection,rightsRolesRepository);
        clientRepository.removeAll();
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
        Client client = new Client();
        client.setName("name1");
        client.setIdCardNumber("12234");
        client.setCnp("6220309019384");
        client.setAddress("address");
        clientRepository.save(client);

        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 1);
    }
    @Test
    public void update() throws Exception{
        Client client = new Client();
        client.setId(1L);
        client.setName("name<3");
        client.setIdCardNumber("12234");
        client.setCnp("6220309019384");
        client.setAddress("address");
        clientRepository.save(client);
        client.setName("name4");
        assertTrue(clientRepository.update(client));
    }

    @Test
    public void save() throws Exception {

        Client client = new Client();
        client.setName("name");
        client.setIdCardNumber("12234");
        client.setCnp("6220309019384");
        client.setAddress("address");
        assertTrue(clientRepository.save(client));
    }

    @Test
    public void removeAll() throws Exception {
        clientRepository.removeAll();
        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 0);
    }
}
