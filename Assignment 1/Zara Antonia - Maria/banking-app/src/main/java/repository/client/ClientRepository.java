package repository.client;

import model.Client;

import java.util.ArrayList;
import java.util.Optional;

public interface ClientRepository {

    ArrayList<Client> findAll();

    Optional<Client> findById(Integer id);

    boolean update(Client client);

    boolean add(Client client);

    boolean removeAll();
}
