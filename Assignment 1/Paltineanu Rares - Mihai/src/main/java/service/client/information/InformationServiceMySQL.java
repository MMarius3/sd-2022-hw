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
    public boolean save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void update(Client oldEntity, Client newEntity) {

    }

    @Override
    public Client view(Long id) {
        return clientRepository.findById(id);
    }
}
