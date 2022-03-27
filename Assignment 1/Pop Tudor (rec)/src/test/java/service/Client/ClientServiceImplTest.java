package service.Client;

import database.DataBaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.EntityNotFoundException;
import repository.Client.ClientRepositoryMySQL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceImplTest {
    private static ClientServiceImpl service;

    @BeforeEach
    public void setUp() {

        JDBConnectionWrapper connectionWrapper = DataBaseConnectionFactory.getConnectionWrapper(true);

        service = new ClientServiceImpl(new ClientRepositoryMySQL(connectionWrapper.getConnection()));

    }

    @Test
    void findAll() {
        List<Client> all = service.findAll();
        assertTrue(all.isEmpty());
    }

    @Test
    void findById() throws EntityNotFoundException {

        List<Client> allClients = service.findAll();
        Long current = allClients.get(allClients.size()-1).getId();

        final Client client = new ClientBuilder().setName("Putin").
                setAddress("strada Razboiului").
                setIdentificationNr(66666666).
                setPersonalNumericalCode("1981123669").
                build();
        service.save(client);

        assertNotNull(service.findById(current + 1));

    }

    @Test
    void save() {

        final Client testClient = new  ClientBuilder().setName("John Cena").
                setAddress("strada YouCantSeeMe").
                setIdentificationNr(99991111).
                setPersonalNumericalCode("1981123669").
                build();

        assertTrue(service.save(testClient).getResult());

    }

    @Test
    void removeAll() {
        final Client testClient = new ClientBuilder().setName("Big Show").
                setAddress("strada BigFat").
                setIdentificationNr(11119999).
                setPersonalNumericalCode("1981123669")
                .build();
        service.save(testClient);
        service.removeAll();
        List<Client> allClients = service.findAll();
        assertTrue(allClients.isEmpty());


    }

    @Test
    void remove() {

        final Client testClient = new ClientBuilder().setName("Ana Maria").
                setAddress("strada Galaxiei").
                setIdentificationNr(11211211).
                setPersonalNumericalCode("299061608").
                build();
        service.update(testClient);
        assertTrue(service.remove(testClient.getId()));

    }

    @Test
    void update() {

        final Client testClient = new ClientBuilder().setName("Randy Orton").
                setAddress("strada RKO").
                setIdentificationNr(91191191).
                setPersonalNumericalCode("1981123669").
                build();
        testClient.setAddress("strada Viperei");
        assertTrue(service.save(testClient).getFormattedErrors().isEmpty());

    }
}