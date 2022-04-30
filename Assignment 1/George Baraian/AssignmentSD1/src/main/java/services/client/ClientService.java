package services.client;

import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientService {

    List<Client> findAll();

    Notification<Boolean> save(Client client);

    Client findById(Long id);

    void updateName(Long id, String name);

    Notification<Boolean> updateClientInformation(String newName, String newAddress, Long newPersonalNumericalCode, Long id);

}
