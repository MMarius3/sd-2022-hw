package repository;

import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.*;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static common.TestEntityGenerator.generateClientForTest;
import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryMySQLTest {
  private static JDBConnectionWrapper connectionWrapper;
  private static ClientRepository clientRepository;

  @BeforeAll
  static void setUp() {
    connectionWrapper = DBConnectionFactory.getConnectionWrapper(true);
    clientRepository = new ClientRepositoryMySQL(connectionWrapper.getConnection());
  }

  @AfterAll
  static void tearDown() throws SQLException {
    connectionWrapper.getConnection().close();
  }

  @BeforeEach
  void clean() {
    clientRepository.deleteAll();
  }

  @Test
  void findAll() {
    final List<Client> clients = clientRepository.findAll();
    assertTrue(clients.isEmpty());

    int nrInsertedAccounts = 10;
    for(int i = 1; i <= nrInsertedAccounts; i++) {
      Client client = generateClientForTest((long) i);
      assertTrue(clientRepository.save(client));
    }

    final List<Client> insertedClients = clientRepository.findAll();
    assertEquals(nrInsertedAccounts, insertedClients.size());
  }

  @Test
  void findById_Empty_InvalidId() {
    assertTrue(clientRepository.findById(-1L).isEmpty());
  }

  @Test
  void findById_Present_ValidId() {
    final Client client = generateClientForTest(1L);
    assertTrue(clientRepository.save(client));

    final Optional<Client> clientOptional = clientRepository.findById(1L);
    assertTrue(clientOptional.isPresent());
    assertEquals(client.getName(), clientOptional.get().getName());
    assertEquals(client.getCardNumber(), clientOptional.get().getCardNumber());
    assertEquals(client.getSSN(), clientOptional.get().getSSN());
    assertEquals(client.getAddress(), clientOptional.get().getAddress());
  }

  @Test
  void save_invalid() {
    final Client invalidClient = new ClientBuilder().build();
    assertFalse(clientRepository.save(invalidClient));
  }

  @Test
  void save_valid() {
    final Client noGivenIdClient = generateClientForTest(0L);
    assertTrue(clientRepository.save(noGivenIdClient));

    final Client givenIdClient = generateClientForTest(13L);
    assertTrue(clientRepository.save(givenIdClient));

    assertTrue(clientRepository.findById(13L).isPresent());
  }

  @Test
  void update() {
    final Client client = generateClientForTest(1L);
    assertTrue(clientRepository.save(client));

    final Client updateClient = generateClientForTest(2L);
    assertNotEquals(client.getCardNumber(), updateClient.getCardNumber());

    assertTrue(clientRepository.update(1L, updateClient));
    final Optional<Client> clientOptional = clientRepository.findById(1L);
    assertTrue(clientOptional.isPresent());
    assertEquals(client.getId(), clientOptional.get().getId());
    assertEquals(updateClient.getCardNumber(), clientOptional.get().getCardNumber());
  }

  @Test
  void deleteById() {
    final Client client = generateClientForTest(1L);
    assertTrue(clientRepository.save(client));
    assertTrue(clientRepository.deleteById(1L));

    assertTrue(clientRepository.findById(1L).isEmpty());
  }

  @Test
  void deleteAll() {
    int nrInsertedAccounts = 10;
    for(int i = 1; i <= nrInsertedAccounts; i++) {
      Client client = generateClientForTest((long) i);
      assertTrue(clientRepository.save(client));
    }

    clientRepository.deleteAll();
    final List<Client> allClients = clientRepository.findAll();
    assertTrue(allClients.isEmpty());
  }
}