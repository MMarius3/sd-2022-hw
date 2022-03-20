package repository.client;

import model.Client;

import java.util.Date;
import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findByNameInfo(String name);

    boolean updateInfo(String name, Long cardId, String CNP, String address);

    Optional<Client> findByID(Long id);

    //boolean create(String name, Long cardId, String CNP, String address, Long id, int balance, String type, Date date);
    boolean create(Client client);

    boolean updateAccount(Client client);

    void remove(Long id);
}
