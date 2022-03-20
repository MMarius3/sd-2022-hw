package service.client;

import model.Client;
import repository.book.BookRepository;
import repository.client.ClientRepository;
import service.client.ClientService;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client findByNameInfo(String name) {
        return repository.findByNameInfo(name)
                .orElseThrow(() -> new IllegalArgumentException("Client with name %s not found".formatted(name)));
    }

    @Override
    public boolean updateInfo(String name, Long cardId, String CNP, String address) {
        return repository.updateInfo(name,cardId,CNP,address);
    }

    @Override
    public void removeByNameInfo(String name) {

    }

    @Override
    public Client findByNameAccount(String name) {
        return null;
    }

    @Override
    public boolean create(Client client) {
        return false;
    }

    @Override
    public boolean updateAccount(Client client) {
        return false;
    }

    @Override
    public void removeByNameAccount(String name) {

    }
}
