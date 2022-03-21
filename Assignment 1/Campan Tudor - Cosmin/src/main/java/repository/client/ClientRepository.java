package repository.client;

import model.Client;

import java.util.Optional;

public interface ClientRepository {
    Client findByName(String name);
    boolean save(Client client);
    void deleteByName(String name);
    void updateByName(String name,Client c2);
}
