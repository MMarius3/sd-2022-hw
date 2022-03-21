package service.client;

import model.Client;
import model.validation.ClientValidator;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Notification<Boolean> save(Client client) {
        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> saveClientNotification = new Notification<>();

        List<Client> allClients = clientRepository.findAll();
        for (Client client1 : allClients) {
            if (client1.getPersonalNumericalCode().equals(client.getPersonalNumericalCode())) {
                saveClientNotification.addError("Personal numerical codes must be unique!");
                saveClientNotification.setResult(Boolean.FALSE);
                return saveClientNotification;
            }
        }

        if (!clientValid) {
            clientValidator.getErrors().forEach(saveClientNotification::addError);
            saveClientNotification.setResult(Boolean.FALSE);
        } else {
            saveClientNotification.setResult(clientRepository.save(client));
        }
        return saveClientNotification;
    }

    @Override
    public Notification<Client> viewClient(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Notification<Boolean> updateClient(Client client) {
        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> updateClientNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(updateClientNotification::addError);
            updateClientNotification.setResult(Boolean.FALSE);
        } else {
            updateClientNotification.setResult(clientRepository.update(client));
        }
        return updateClientNotification;
    }
}
