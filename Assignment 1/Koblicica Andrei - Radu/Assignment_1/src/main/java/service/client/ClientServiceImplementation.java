package service.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Client;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceImplementation implements ClientService{
    private final ClientRepository repository;

    public ClientServiceImplementation(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public ObservableList<Client> findAll() {
        ObservableList<Client> clients= FXCollections.observableArrayList();
        for(Client client: repository.findAll()){
            clients.add(client);
        }
        return clients;
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
    public boolean edit(Client client) {
        return repository.edit(client);
    }
}
