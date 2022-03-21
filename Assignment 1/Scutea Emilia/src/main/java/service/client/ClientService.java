package service.client;

import controller.Response;
import model.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();

    Client findById(Long id);

    boolean save(Client client);

    Client findByCardNumber(Long cardNumber);

    boolean update(Client client);

    void removeAll();

    Response<Boolean> existsByCNP(Long personalNumericalCode);

    Response<Boolean> existsByName(String name);
}
