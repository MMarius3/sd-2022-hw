package repository.client;
;
import controller.Response;
import model.Client;
import model.User;

import java.util.List;

public interface ClientRepository {

    List<Client> findAll();

    Client findByNumericalCode(String numericalCode);
    Client findById(Long id);

    boolean save(Client client);

    void removeAll();

    void removeClient(String personal_numerical_code);

    void updateClient( String name, String id_card_number, String personal_numerical_code, String address);

    Response<Boolean> existsByNumericalCode(String numericalCode);

    Response<Boolean> existsById(String id);
}