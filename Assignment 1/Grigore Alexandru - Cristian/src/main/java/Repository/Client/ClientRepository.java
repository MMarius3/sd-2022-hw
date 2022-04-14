package Repository.Client;

import Model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> findAll();

    Optional<Client> findById(Long id);

    boolean save(Client client);

    void removeAll();
}
