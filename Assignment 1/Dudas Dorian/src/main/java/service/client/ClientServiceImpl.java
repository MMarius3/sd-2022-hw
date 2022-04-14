package service.client;

import model.Client;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.util.List;

public class ClientServiceImpl implements ClientService{
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
                .orElseThrow(() -> new IllegalArgumentException("Client with id %d not found".formatted(id)));
    }

    @Override
    public boolean save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public boolean updateById(Long id, Client client) {
        return clientRepository.updateById(id, client);
    }

    @Override
    public void removeAll() {
        clientRepository.removeAll();
    }
}
