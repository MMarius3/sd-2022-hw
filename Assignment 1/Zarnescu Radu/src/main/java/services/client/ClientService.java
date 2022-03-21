package services.client;

import controller.Response;
import model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientService {
    List<Client> findAll() throws SQLException;

    Client findById(Long id);

    boolean save(Client client);

    boolean update(Client client);

    boolean delete(Client client);

    Response<Boolean> existsByCardNr(String cardNr);

    Response<Boolean> existsByPnc(String pnc);
}
