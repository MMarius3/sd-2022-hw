package repositories;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.sql.Connection;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryMySQLTest {

    private static ClientRepository clientRepository;


    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository=new ClientRepositoryMySQL(connection);
    }

    @BeforeEach
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    public void findById() throws Exception {
        Long id = 1L;
        assertNull(clientRepository.findById(id));
    }

    @Test
    public void save() throws Exception {
        assertTrue(clientRepository.save(
                new ClientBuilder()
                        .setIdCardNumber("342423423")
                        .setPersonalNumericalCode("5010425125777")
                        .setAddress("Valeriu Bologa 3")
                        .setName("Dominic")
                        .build()));
    }

}
