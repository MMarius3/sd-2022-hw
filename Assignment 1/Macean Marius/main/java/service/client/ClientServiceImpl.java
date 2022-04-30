package service.client;

import model.Client;
import repository.client.ClientRepository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public boolean addClient(String name, Integer idCardNumber, Long idCode) {
        Client client = new Client();
        client.setName(name);
        client.setIdCardNumber(idCardNumber);
        client.setIdCode(idCode);
        return save(client);
    }

    @Override
    public boolean updateClient(Long id, String name, Integer idCardNumber, Long idCode) {
        Client client = repository.findById(id).orElse(new Client());
        client.setName(name);
        client.setIdCardNumber(idCardNumber);
        client.setIdCode(idCode);
        return update(client);
    }

    @Override
    public Client viewClient(Long id) {
        return findById(id);
    }
}