package service.client;

import model.Client;
import repository.client.ClientRepository;

import java.util.List;
import java.util.Optional;

public class ClientServiceMySQL implements ClientService{
    private final ClientRepository clientRepository;

    public ClientServiceMySQL(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean addClient(Client client) {
        return clientRepository.addClient(client);
    }

    @Override
    public List<Client> viewClients() {
        return clientRepository.viewClients();
    }

    @Override
    public Optional<Client> findClient(Integer id) {
        return clientRepository.findClient(id);
    }

    @Override
    public boolean updateClient(Client client) {
        return clientRepository.updateClient(client);
    }

    @Override
    public void removeAll() {
        clientRepository.removeAll();
    }
}
