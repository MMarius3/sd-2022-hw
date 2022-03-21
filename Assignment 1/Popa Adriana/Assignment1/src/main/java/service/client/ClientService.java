package service.client;

import model.Client;
import repository.client.ClientRepository;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    boolean addClient(Client client);

    List<Client> viewClients();

    Optional<Client> findClient(Integer id);

    boolean updateClient(Client client);

    void removeAll();
}
