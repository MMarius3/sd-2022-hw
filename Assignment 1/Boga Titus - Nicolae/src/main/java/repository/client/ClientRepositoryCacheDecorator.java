package repository.client;

import model.Client;
import repository.Cache;

import java.util.List;
import java.util.Optional;

public class ClientRepositoryCacheDecorator extends ClientRepositoryDecorator {

  private Cache<Client> cache;

  public ClientRepositoryCacheDecorator(ClientRepository clientRepository, Cache<Client> cache) {
    super(clientRepository);
    this.cache = cache;
  }

  @Override
  public List<Client> findAll() {
    if (cache.hasResult()) {
      return cache.load();
    }
    List<Client> clients = decoratedRepository.findAll();
    cache.save(clients);
    return clients;
  }

  @Override
  public Optional<Client> findById(Long id) {
    return decoratedRepository.findById(id);
  }

  @Override
  public boolean save(Client client) {
    cache.invalidateCache();
    return decoratedRepository.save(client);
  }

  @Override
  public boolean update(Client client) {
    return false;
  }

  @Override
  public void removeAll() {
    cache.invalidateCache();
    decoratedRepository.removeAll();
  }
}
