package repository.client;

import database.DBConnectionFactory;
import model.Client;
import model.builder.BookBuilder;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryMySQLTest {
    private static ClientRepositoryMySQL clientRepository;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);

    }

    @Test
    public void save() throws Exception {
        assertTrue(clientRepository.save(
                new ClientBuilder()
                        .setName("Title")
                        .setIdcardnumber((long) 123456789)
                        .setCnp((long) 12345)
                        .setAddress("Baritiu 28")
                        .build()
        ));
    }

    @Test
    public void findByName() throws Exception {
        clientRepository.save(
                new ClientBuilder()
                        .setName("Title")
                        .setIdcardnumber((long) 123456789)
                        .setCnp((long) 12345)
                        .setAddress("Baritiu 28")
                        .build()
        );
        assertNotNull(clientRepository.findByName("Title"));
    }
    @Test
    public void updateByName() throws Exception {
        Client c2=new ClientBuilder()
                .setName("Title2")
                .setIdcardnumber((long) 12345987)
                .setCnp((long) 12543)
                .setAddress("Baritiu 27")
                .build();
        clientRepository.updateByName("Title",c2);
        assertNotNull(clientRepository.findByName("Title2"));
    }
    @Test
    public void deleteByName() throws Exception {
        Client c2=new ClientBuilder()
                .setName("Title2")
                .setIdcardnumber((long) 12345987)
                .setCnp((long) 12543)
                .setAddress("Baritiu 27")
                .build();
        clientRepository.save(c2);
        clientRepository.deleteByName("Title2");
        assertEquals(clientRepository.findByName("Title2"),null);
    }


}


