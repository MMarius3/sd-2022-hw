import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import model.Client;
import model.builder.ClientBuilder;
import repository.Client.ClientRepository;
import repository.Client.ClientRepositoryMySQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryMySQLTest {

    private static ClientRepository repository;

    @BeforeAll
    public static void setupClass(){
        JDBConnectionWrapper connectionWrapper = DBConnectionFactory.getConnectionWrapper(true);
        repository = new ClientRepositoryMySQL(connectionWrapper.getConnection());
    }

    @BeforeEach
    public void setup(){
        repository.removeAll();
    }

    @Test
    public void findAll() throws SQLException {
        List<Client> noClients = repository.findAll();
        assertTrue(noClients.isEmpty());

        Client client = new ClientBuilder()
                .setFullName("User")
                .setCardNumber("5050-1023-2094-4331")
                .setPnc("2848372")
                .setAddress("Str. test, nr. 1")
                .build();

        Client client2 = new ClientBuilder()
                .setFullName("User2")
                .setCardNumber("5050-1021-2094-4331")
                .setPnc("2848142")
                .setAddress("Str. test, nr. 2")
                .build();

        Client client3 = new ClientBuilder()
                .setFullName("User3")
                .setCardNumber("5050-1021-2094-4300")
                .setPnc("28481425")
                .setAddress("Str. test, nr. 3")
                .build();

        repository.save(client);
        repository.save(client2);
        repository.save(client3);

        List<Client> clients = repository.findAll();
        assertEquals(3, clients.size());
    }

//    @Test
//    public void findById(){
//        User user = new UserBuilder()
//                .setFullName("User")
//                .setCardNumber("5050-1023-2094-4331")
//                .setPnc("2848372")
//                .setAddress("Str. test, nr. 1")
//                .build();
//
//        repository.save(user);
//
//        User user1 = repository.findById(12L)
//        .orElseThrow(() -> new IllegalArgumentException(format("Book with id %d not found", 12L)));;
//
//        assertTrue(user.getId() == user1.getId());
//
//    }

    @Test
    public void save(){
        Client client = new ClientBuilder()
                .setFullName("User")
                .setCardNumber("5050-1023-2094-4331")
                .setPnc("2848372")
                .setAddress("Str. test, nr. 1")
                .build();

        assertTrue(repository.save(client));

        Client clientInvalid = new ClientBuilder()
                .setFullName("User")
                .setPnc("2848372")
                .setAddress("Str. test, nr. 1")
                .build();

        assertFalse(repository.save(clientInvalid));
    }

    @Test
    public void removeAll() throws SQLException {
        repository.save(new ClientBuilder()
                .setFullName("User")
                .setCardNumber("5050-1023-2094-4331")
                .setPnc("2848372")
                .setAddress("Str. test, nr. 1")
                .build());
        repository.removeAll();
        List<Client> noBooks = repository.findAll();
        assertTrue(noBooks.isEmpty());
    }
}
