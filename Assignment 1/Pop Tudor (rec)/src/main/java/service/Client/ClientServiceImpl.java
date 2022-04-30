package service.Client;

import model.Client;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.Client.ClientRepository;
import repository.EntityNotFoundException;

import java.util.List;

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
    public Client findById(Long id) throws EntityNotFoundException {
        return repository.findById(id);
    }

    @Override
    public Notification<Boolean> save(Client client) {
        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(clientNotification::addError);
            clientNotification.setResult(Boolean.FALSE);
        } else
            clientNotification.setResult(repository.save(client));
        return clientNotification;
    }

    @Override
    public void removeAll() {
        repository.removeAll();
    }

    @Override
    public boolean remove(Long id) {
        return repository.remove(id);
    }

    @Override
    public Notification<Boolean> update(Client client) {
        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(clientNotification::addError);
            clientNotification.setResult(Boolean.FALSE);
        } else
            clientNotification.setResult(repository.update(client));
        return clientNotification;
    }
}
