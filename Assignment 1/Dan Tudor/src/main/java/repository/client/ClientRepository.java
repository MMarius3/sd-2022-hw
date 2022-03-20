package repository.client;

import model.Client;

import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findByNameInfo(String name);


    boolean updateInfo(String name, Long cardId, String CNP, String address);

    void removeByNameInfo(String name);

    Optional<Client> findByNameAccount(String name);

    boolean create(Client client);

    boolean updateAccount(Client client);

    void removeByNameAccount(String name);
}
