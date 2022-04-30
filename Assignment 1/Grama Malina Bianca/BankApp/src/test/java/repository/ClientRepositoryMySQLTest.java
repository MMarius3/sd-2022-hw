package repository;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryMySQLTest {

    private static ClientRepository clientRepository;

    @BeforeAll
    public static void setupClass() {
        clientRepository = new ClientRepositoryMySQL(
                new DBConnectionFactory().getConnectionWrapper(true).getConnection());
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
        Client client1 = new ClientBuilder()
                .setName("Name")
                .setIdCardNo("MS010203")
                .setCNP("1234567891234")
                .setAddress("address")
                .build();
        clientRepository.save(client1);

        Client client2 = new ClientBuilder()
                .setName("Name")
                .setIdCardNo("MS010204")
                .setCNP("1234567891235")
                .setAddress("address")
                .build();
        clientRepository.save(client2);

        Client client3 = new ClientBuilder()
                .setName("Name")
                .setIdCardNo("MS010205")
                .setCNP("1234567891236")
                .setAddress("address")
                .build();
        clientRepository.save(client3);

        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 3);
    }

    @Test
    public void findByCNP() throws Exception {
        Client client1 = new ClientBuilder()
                .setName("Name")
                .setIdCardNo("MS010203")
                .setCNP("1234567891234")
                .setAddress("address")
                .build();
        clientRepository.save(client1);

        Client client2 = new ClientBuilder()
                .setName("Name")
                .setIdCardNo("MS010204")
                .setCNP("1234567891235")
                .setAddress("address")
                .build();
        clientRepository.save(client2);

        Client client3 = new ClientBuilder()
                .setName("Name")
                .setIdCardNo("MS010205")
                .setCNP("1234567891236")
                .setAddress("address")
                .build();
        clientRepository.save(client3);

        Client client = clientRepository.findByCNP("1234567891236");
        assertEquals(client3.getCNP(), client.getCNP());
    }

    @Test
    public void updateName() {
        Client client = new ClientBuilder()
                .setName("Name")
                .setIdCardNo("MS000000")
                .setCNP("4512487965234")
                .setAddress("address")
                .build();
        clientRepository.save(client);
        assertTrue(clientRepository.updateName(client, "Andrei"));
    }

    @Test
    public void updateICNum() {
        Client client = new ClientBuilder()
                .setName("Name")
                .setIdCardNo("MS000000")
                .setCNP("4512487965234")
                .setAddress("address")
                .build();
        clientRepository.save(client);
        assertTrue(clientRepository.updateICNum(client, "MS999999"));
    }

    @Test
    public void updateCNP() {
        Client client = new ClientBuilder()
                .setName("Name")
                .setIdCardNo("MS000000")
                .setCNP("4512487965234")
                .setAddress("address")
                .build();
        clientRepository.save(client);
        assertTrue(clientRepository.updateCNP(client, "1234567891234"));
    }

    @Test
    public void updateAddress() {
        Client client = new ClientBuilder()
                .setName("Name")
                .setIdCardNo("MS000000")
                .setCNP("4512487965234")
                .setAddress("address")
                .build();
        clientRepository.save(client);
        assertTrue(clientRepository.updateAddress(client, "Str Fericirii"));
    }

    @Test
    public void save() throws Exception {
        assertNotEquals(-1L, clientRepository.save(
                new ClientBuilder()
                        .setName("Name")
                        .setIdCardNo("MS010210")
                        .setCNP("5484325874125")
                        .build()
        ));
    }

    @Test
    public void removeAll() throws Exception {

    }

}
