package repository;


import database.DatabaseConnectionFactory;
import database.DatabaseInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepositoryMySQL;
import dtos.ClientDTO;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientRepositoryMySQLTest {

    private static ClientRepositoryMySQL clientRepositoryMySQL;

    @BeforeAll
    public static void setupClass() {
        clientRepositoryMySQL = new ClientRepositoryMySQL(true);
    }
//
//    @BeforeEach
//    public void setup() {
//        clientRepositoryMySQL.deleteAll();
//    }
//
//    @AfterAll
//    public void deleteDB(String databaseName) {
//        DatabaseInitializer.dropDatabase(databaseName);
//    }

    @Test
    public void deleteByID() {
        try {
            Connection connection = DatabaseConnectionFactory.getConnection(true);
            DatabaseInitializer.setConnection(connection);
            DatabaseInitializer.loadSchema(connection.getCatalog());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteAll() {
        clientRepositoryMySQL.deleteAll();
        assertTrue(clientRepositoryMySQL.getAll() == null);
    }

    @Test
    public void create() {
        long clientID = clientRepositoryMySQL.create(new ClientDTO().setAddress("address").setName("name").setIdentityCardNumber("icn").setPersonalNumericCode("pnc"));
        assertTrue(clientID > 0);
    }

    @Test
    public void getByID() {
        ClientDTO clientDTO = clientRepositoryMySQL.getByID(1);
        assertEquals("name0", clientDTO.getName());
        assertEquals("identity_card_number0", clientDTO.getIdentityCardNumber());
        assertEquals("personal_numeric_code0", clientDTO.getPersonalNumericCode());
        assertEquals("address0", clientDTO.getAddress());
    }

    @Test
    public void update() {

    }

    @Test
    public void getAll() {

    }
}
