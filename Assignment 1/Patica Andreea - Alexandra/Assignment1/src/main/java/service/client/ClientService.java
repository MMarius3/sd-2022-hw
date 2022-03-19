package service.client;

import model.Book;
import model.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();

    Client findById(Long id);

    Client findByName(String name);

    boolean save(Client client);


}
