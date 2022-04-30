package service.book;

import model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepositoryMock;
import service.client.ClientService;
import service.client.ClientServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientServiceImplTest {

  private ClientService clientService;

  @BeforeEach
  public void setup() {
    clientService = new ClientServiceImpl(new ClientRepositoryMock());
  }

  @Test
  public void findAll() throws Exception {
    assertEquals(0, clientService.findAll().size());
  }

  @Test
  public void findById() throws Exception {
    Long id = 1L;
    assertThrows(IllegalArgumentException.class, () -> clientService.findById(id));
  }

  @Test
  public void save() throws Exception {
    assertTrue(clientService.save(new Client()));
  }

}
