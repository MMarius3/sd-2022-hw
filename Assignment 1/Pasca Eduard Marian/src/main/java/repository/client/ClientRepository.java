package repository.client;

import model.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();

    boolean save(Client client);

    boolean update(Client client);

}
