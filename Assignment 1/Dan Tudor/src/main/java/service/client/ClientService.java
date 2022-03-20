package service.client;

import model.Client;

import java.util.Optional;

public interface ClientService {
    Client findByNameInfo(String name);

    boolean updateInfo(String name, Long cardId, String CNP, String address);

    void removeByNameInfo(String name);

    Client findByNameAccount(String name);

    boolean create(Client client);

    boolean updateAccount(Client client);

    void removeByNameAccount(String name);
}
