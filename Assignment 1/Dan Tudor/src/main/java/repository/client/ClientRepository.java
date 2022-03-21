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

    boolean updateBalance(String name, int balance);

    boolean updateTransfer(String name1, String name2, int balance1, int balance2);
}
