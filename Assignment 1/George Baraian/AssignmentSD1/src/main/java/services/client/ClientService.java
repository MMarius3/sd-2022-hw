package services.client;

import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientService {

    List<Client> findAll();

    Notification<Boolean> save(Client client);

    Client findById(Long id);

}
