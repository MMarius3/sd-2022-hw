package service.client;

import model.Book;
import model.Client;
import repository.book.BookRepository;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceImpl implements ClientService{

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

    public Client findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Client with name %s not found".formatted(name)));
    }

    @Override
    public boolean save(Client client) {
        return repository.save(client);
    }

}
