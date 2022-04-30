package repositories.client;

import launcher.ComponentFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientRepositoryMySQLTest {

    private static ClientRepository clientRepository;

    @BeforeEach
    void cleanup(){
        clientRepository.removeAll();
    }

    @BeforeAll
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        clientRepository = componentFactory.getClientRepository();
    }

    @Test
    void findAll() {

    }

    @Test
    void findById() {
        Client client = new ClientBuilder().setId(5L).setName("George").setAddress("aici").setPersonalNumericalCode(34L).build();
        clientRepository.save(client);
        assertTrue(clientRepository.findById(5L).getName().equals("George"));
    }

    @Test
    void updateName() throws Exception{
        Client client1 = new ClientBuilder().setId(1L).setName("George").setAddress("acolo").setPersonalNumericalCode(321L).build();
        clientRepository.save(client1);
        clientRepository.updateName("Andrei", 1L);
        assertTrue(clientRepository.findById(1L).getName().equals("Andrei"));
    }

    @Test
    void updatePersonalNumericalCode() {
    }

    @Test
    void updateAddress() {
    }

    @Test
    public void save() throws Exception{
        assertTrue(clientRepository.save(
                new ClientBuilder()
                        .setId(1L)
                        .setName("Andrei")
                        .setAddress("acasa")
                        .setPersonalNumericalCode(1234L)
                        .build()
        ));
    }

    @Test
    void removeAll() {
    }
}