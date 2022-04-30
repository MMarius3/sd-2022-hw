package service.client;

import model.Client;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceImpl implements ClientService {

  private final ClientRepository repository;

  public ClientServiceImpl(ClientRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Client> findAll() {
    return repository.findAll();
  }

  @Override
  public Client findById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Client with id %d not found".formatted(id)));
  }

  @Override
  public boolean save(Client client) {
    return repository.save(client);
  }

  @Override
  public boolean update(Client client) {
    return repository.update(client);
  }



  /*
  @Override
  public int getAgeOfBook(Long id) {
    return repository.findById(id)
        .map(Client::getAge)
        .orElse(0);
  }
*/

}
