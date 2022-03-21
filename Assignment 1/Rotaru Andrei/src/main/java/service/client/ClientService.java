package service.client;

import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientService {
    List<Client> findAll();

    Notification<Boolean> save(Client client);

    Notification<Client> viewClient(Long id);

    Notification<Boolean> updateClient(Client client);
}
