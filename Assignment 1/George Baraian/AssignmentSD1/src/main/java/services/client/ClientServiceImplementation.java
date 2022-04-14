package services.client;

import model.Client;
import model.validation.Notification;
import repositories.client.ClientRepository;

import java.util.List;

public class ClientServiceImplementation implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImplementation(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Notification<Boolean> save(Client client) {
        Client client1 = client;
        Notification<Boolean> clientNotification = new Notification<>();

        clientNotification.setResult(clientRepository.save(client1));

        return clientNotification;
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id);
    }
}
