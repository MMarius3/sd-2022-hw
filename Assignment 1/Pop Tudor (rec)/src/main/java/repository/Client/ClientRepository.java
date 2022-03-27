package repository.Client;
import model.Client;
import repository.EntityNotFoundException;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();

    Client findById(Long id) throws EntityNotFoundException;

    boolean save(Client client);

    void removeAll();

    boolean remove(Long id);

    boolean update(Client client);

}
