package service.client;

import model.Client;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceImpl implements ClientService {

  private final ClientRepository clientRepository;

  public ClientServiceImpl(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public List<Client> findAll() {
    return clientRepository.findAll();
  }

  @Override
  public Client findById(Long id) {
    return clientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No such id"));
  }

  @Override
  public boolean save(Client client) {
    return clientRepository.save(client);
  }

  @Override
  public boolean update(Long id, Client client) {
    return clientRepository.update(id, client);
  }

  @Override
  public boolean deleteById(Long id) {
    return clientRepository.deleteById(id);
  }

  @Override
  public void deleteAll() {
    clientRepository.deleteAll();
  }
}
