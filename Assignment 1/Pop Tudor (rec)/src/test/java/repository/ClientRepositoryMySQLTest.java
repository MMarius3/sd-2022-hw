package repository;

import database.DataBaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.Client.ClientRepository;
import repository.Client.ClientRepositoryMySQL;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryMySQLTest {

    private static ClientRepository repository;

    @BeforeEach
    void setUp() {
        //daca vreau sa folosesc baza de date test_OrderDB
       JDBConnectionWrapper connectionWrapper = DataBaseConnectionFactory.getConnectionWrapper(true);

        //daca vreau sa folosesc baza de date orderDB
        //connectionWrapper = DataBaseConnectionFactory.getConnectionWrapper(false);

        repository = new ClientRepositoryMySQL(connectionWrapper.getConnection());
    }

    @BeforeEach
    public void setup(){
        repository.removeAll();
    }

    @Test
    void findAll() {

        List<Client> allClients = repository.findAll();
        assertTrue(allClients.isEmpty());

    }

    @Test
    void findById() throws EntityNotFoundException {

        List<Client> allClients = repository.findAll();
        Long current = allClients.get(allClients.size()-1).getId();

        final Client client = new ClientBuilder().setName("Putin").
                                                setAddress("strada Razboiului").
                                                setIdentificationNr(66666666).
                                                setPersonalNumericalCode("1981123QWE669").
                                                build();
        repository.save(client);

        assertNotNull(repository.findById(current + 1));

    }

    @Test
    void save() {

        final Client testClient = new  ClientBuilder().setName("John Cena").
                                                    setAddress("strada YouCantSeeMe").
                                                    setIdentificationNr(99991111).
                                                    setPersonalNumericalCode("1981123RTY669").
                                                    build();
        assertTrue(repository.save(testClient));

    }

    @Test
    void removeAll() {

        final Client testClient = new ClientBuilder().setName("Big Show").
                                                setAddress("strada BigFat").
                                                setIdentificationNr(11119999).
                                                setPersonalNumericalCode("1981123UIO669")
                                                .build();
        repository.save(testClient);
        repository.removeAll();
        final List <Client> allClients = repository.findAll();
        assertTrue(allClients.isEmpty());

    }

    @Test
    void remove() {

        final Client testClient = new ClientBuilder().setName("Ana Maria").
                                                setAddress("strada Galaxiei").
                                                setIdentificationNr(11211211).
                                                setPersonalNumericalCode("2990616ASDF08").
                                                build();
        repository.save(testClient);
        assertTrue(repository.remove(testClient.getId()));

    }

    @Test
    void update() {

        final Client testClient = new ClientBuilder().setName("Randy Orton").
                                                setAddress("strada RKO").
                                                setIdentificationNr(91191191).
                                                setPersonalNumericalCode("1981123PAS669").
                                                build();
        testClient.setName("Randy Ortgone");
        assertTrue(repository.update(testClient));
    }
}