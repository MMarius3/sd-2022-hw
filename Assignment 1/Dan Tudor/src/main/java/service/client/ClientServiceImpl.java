package service.client;

import model.Client;
import repository.book.BookRepository;
import repository.client.ClientRepository;
import service.client.ClientService;

import java.util.Date;

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
    public Client findByID(Long id) {
        return repository.findByID(id)
                .orElseThrow(() -> new IllegalArgumentException("Client with name %d not found".formatted(id)));
    }

    @Override
    public boolean create(Client client) {
        return repository.create(client);
    }

    @Override
    public boolean updateAccount(Client client) {
        return repository.updateAccount(client);
    }

    @Override
    public void remove(Long id) {
        repository.remove(id);
    }

    @Override
    public boolean updateBalance(String name, int balance) {
        return repository.updateBalance(name, balance);
    }

    @Override
    public boolean updateTransfer(String name1, String name2, int balance1, int balance2) {
        return repository.updateTransfer(name1,name2,balance1,balance2);
    }
}
