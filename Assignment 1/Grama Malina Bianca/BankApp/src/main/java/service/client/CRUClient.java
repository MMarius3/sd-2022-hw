package service.client;

import model.Client;
import model.validation.Notification;

import java.util.List;

public interface CRUClient {
    List<Client> viewClients();
    Notification<Boolean> addClient(String name, String cnp, String ICNo, String address);
    boolean updateClient(Long id, Client updatedClient);
    Client searchById(Long id);
}
