package service;

import database.DBConnectionFactory;
import model.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceImpl;


import java.sql.Connection;

import static common.TestEntityGenerator.generateClientForTest;
import static org.junit.jupiter.api.Assertions.*;

class ClientServiceImplTest {

  private static ClientService clientService;

  @BeforeAll
  public static void setup() {
    Connection connection = DBConnectionFactory.getConnectionWrapper(true).getConnection();
    ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
    clientService = new ClientServiceImpl(clientRepository);
  }

  @BeforeEach
  public void cleanup() {
    clientService.deleteAll();
  }

  @Test
  void findAll() {
    assertTrue(clientService.findAll().isEmpty());

    int nrInsertedClients = 10;
    for(int i = 1; i <= nrInsertedClients; i++) {
      Client client = generateClientForTest((long) i);
      clientService.save(client);
    }

    assertEquals(nrInsertedClients, clientService.findAll().size());
  }

  @Test
  void findById() {
    final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> clientService.findById(1L));
    assertEquals("No such id", illegalArgumentException.getMessage());

    final long id = 1L;
    final Client client = generateClientForTest(id);
    assertTrue(clientService.save(client));

    final Client foundClient = clientService.findById(id);
    assertNotNull(foundClient);
    assertEquals(client.getId(), foundClient.getId());
  }

  @Test
  void save() {
    final Client noIdGivenClient = generateClientForTest(0L);
    assertTrue(clientService.save(noIdGivenClient));

    final long id = 1L;
    final Client idGivenClient = generateClientForTest(id);
    assertTrue(clientService.save(idGivenClient));
    assertEquals(id, clientService.findById(id).getId());
  }

  @Test
  void update() {
    final long id = 1L;
    final Client client = generateClientForTest(id);
    assertTrue(clientService.save(client));

    final Client toUpdateClient = generateClientForTest(0L);
    assertTrue(clientService.update(id, toUpdateClient));

    final Client clientUpdated = clientService.findById(id);
    assertEquals(id, clientUpdated.getId());
    assertEquals(toUpdateClient.getAddress(), clientUpdated.getAddress());
  }

  @Test
  void deleteById() {
    final long id = 13L;
    final Client client = generateClientForTest(id);
    assertTrue(clientService.save(client));

    assertTrue(clientService.deleteById(id));

    final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> clientService.findById(id));
    assertEquals("No such id", illegalArgumentException.getMessage());
  }

  @Test
  void deleteAll() {
    for(int i = 1; i <= 10; i++) {
      Client client = generateClientForTest((long) i);
      clientService.save(client);
    }

    clientService.deleteAll();
    assertTrue(clientService.findAll().isEmpty());
  }
}