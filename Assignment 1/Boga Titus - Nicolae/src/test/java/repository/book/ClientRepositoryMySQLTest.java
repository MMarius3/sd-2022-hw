package repository.book;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.Cache;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryCacheDecorator;
import repository.client.ClientRepositoryMySQL;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientRepositoryMySQLTest {

  private static ClientRepository clientRepository;

  @BeforeAll
  public static void setupClass() {
    clientRepository = new ClientRepositoryCacheDecorator(
        new ClientRepositoryMySQL(
            new DBConnectionFactory().getConnectionWrapper(true).getConnection()
        ),
        new Cache<>()
    );
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

 /* @Test
  public void findAllWhenDbNotEmpty() throws Exception {
    Client client = new ClientBuilder()
        .setTitle("Title")
        .setAuthor("Author")
        .setPublishedDate(new Date())
        .build();
    clientRepository.save(client);
    clientRepository.save(client);
    clientRepository.save(client);

    List<Client> clients = clientRepository.findAll();
    assertEquals(clients.size(), 3);
  }
*/
  @Test
  public void findById() throws Exception {

  }

  /*
  @Test
  public void save() throws Exception {
    assertTrue(clientRepository.save(
        new ClientBuilder()
            .setTitle("Title")
            .setAuthor("Author")
            .setPublishedDate(new Date())
            .build()
    ));
  }*/

  @Test
  public void removeAll() throws Exception {

  }

}
