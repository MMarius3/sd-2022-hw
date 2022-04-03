package repository.client;

import controller.Response;
import model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    List<Client> findAll();

    Optional<Client> findById(Long id);

    boolean save(Client client);

    void removeAll();

    boolean update(Client client);

    Response<Boolean> existsByCnp(String cnp);

    Response<Boolean> existsByIdCardNumber(String idCardNumber);
}
