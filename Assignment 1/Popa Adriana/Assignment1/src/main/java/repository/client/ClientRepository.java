package repository.client;

import model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    boolean addClient(Client client);

    List<Client> viewClients();

    Optional<Client> findClient(Integer id);

    boolean updateClient(Client client);

    void removeAll();

}
