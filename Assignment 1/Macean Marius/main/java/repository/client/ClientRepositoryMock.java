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

    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clients.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(Client client) {
        return clients.add(client);
    }

    @Override
    public boolean update(Client client) {
        return clients.add(client);
    }

    @Override
    public void removeAll() {
        clients.clear();
    }
}