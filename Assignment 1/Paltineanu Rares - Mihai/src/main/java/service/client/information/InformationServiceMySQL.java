package service.client.information;

import model.Client;
import repository.client.ClientRepository;
import service.client.ClientService;

import java.util.List;

public class InformationServiceMySQL implements ClientService<Client, Long> {
    private final ClientRepository clientRepository;

    public InformationServiceMySQL(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findall() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public boolean save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public boolean update(Long id, Client newEntity) {

        return clientRepository.update(id, newEntity);
    }

    @Override
    public void removeAll() {
        clientRepository.removeAll();
    }
}
