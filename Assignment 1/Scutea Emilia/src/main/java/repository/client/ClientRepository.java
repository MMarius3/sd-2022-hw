package repository.client;

import controller.Response;
import model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    List<Client> findAll();

    Optional<Client> findById(Long id);

    boolean save(Client client);

    boolean update(Client client);

    Client findByCardNumber(Long cardNumber);

    void removeAll();

    Response<Boolean> existsByCNP(Long personalNumericalCode);

    Response<Boolean> existsByName(String name);
}
