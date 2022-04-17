package repository.book;

import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.Cache;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryCacheDecorator;
import repository.client.ClientRepositoryMock;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientRepositoryMockTest {

  private static ClientRepository repository;

  @BeforeAll
  public static void setupClass() {
    repository = new ClientRepositoryCacheDecorator(
        new ClientRepositoryMock(),
        new Cache<>()
    );
  }

  @Test
  public void findAll() throws Exception {
    assertEquals(0, repository.findAll().size());
  }

  @Test
  public void findById() throws Exception {
    final Optional<Client> book = repository.findById(1L);
    assertTrue(book.isEmpty());
  }
/*
  @Test
  public void save() throws Exception {
    Client client = new ClientBuilder()
        .setId(1L)
        .setTitle("Title")
        .setAuthor("Author")
        .setPublishedDate(new Date())
        .build();

    assertTrue(repository.save(client));
  }
  */
}
