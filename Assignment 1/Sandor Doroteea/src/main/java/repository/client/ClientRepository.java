package repository.client;

import model.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();

    Client findByName(String name);

    boolean save(Client client);

    void removeAll();

    Client findById(Long id);

    boolean removeById(Long id);

    boolean update(Client client);
}
