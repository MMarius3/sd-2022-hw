package repository.client;

import controller.Response;
import model.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();
    Client findByName(String name);
    boolean save(Client client);
    void removeAll();
    Response<Boolean> existsByName(String name);
}
