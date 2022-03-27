package service.client;

import model.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();

    boolean save(Client client);

    boolean update(Client client);
}
