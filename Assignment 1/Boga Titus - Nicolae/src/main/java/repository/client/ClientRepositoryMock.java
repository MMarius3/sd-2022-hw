package repository.client;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepositoryMock implements ClientRepository {

  private List<Client> clients;

  public ClientRepositoryMock() {
    clients = new ArrayList<>();
  }

  public List<Client> findAll() {
    return clients;
  }

  public Optional<Client> findById(Long id) {
    return clients.parallelStream()
        .filter(it -> it.getId().equals(id))
        .findFirst();
  }

  public boolean save(Client client) {
    return clients.add(client);
  }

  @Override
  public boolean update(Client client) {
    return false;
  }

  @Override
  public void removeAll() {
    clients.clear();
  }
}
