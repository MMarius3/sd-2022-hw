package repository.client;

import controller.Response;
import model.Book;
import model.Client;
import model.User;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> findAll();

    Optional<Client> findById(Long id);

    Optional<Client> findByName(String name);

    boolean save(Client client);

    boolean update(Client client);
}
