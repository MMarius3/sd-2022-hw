package respository.client;

import controller.Response;
import model.Client;

import java.util.List;

public interface ClientRepository {

    List<Client> findAll();

    Client findByPNC(Long PNC);

    boolean save(Client client);

    void removeAll();

    Response<Boolean> existsByPNC(Long PNC);

    Response<Boolean> existsByIdCardNr(Long idCardNr);

    Response<Boolean> existsByEmail(String email);

    boolean updateClient(Client client);
}
