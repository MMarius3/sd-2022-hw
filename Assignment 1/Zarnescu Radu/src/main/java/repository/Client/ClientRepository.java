package repository.Client;

import controller.Response;
import model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    List<Client> findAll() throws SQLException;

    Optional<Client> findById(Long id);

    boolean save(Client client);

    boolean update(Client client);

    boolean delete(Client client);

    void removeAll();

    Response<Boolean> existsByField(String fieldName, String fieldValue);
}
