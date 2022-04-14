package repository.client;

import model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> findAll();

    Optional<Client> findById(Long id);

    Optional<Client> findByName(String name);

    boolean save(Client client);

    boolean update(Client client);
}
