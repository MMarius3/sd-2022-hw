package repository.client;

import model.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();
    Client findByID(Long id);
    Client findByCNP(String cnp);
    boolean existsIDCardNo(String idCardNo);
    boolean existsCNP(String cnp);
    long save(Client client);
    boolean update(Long clientId, String name, String cnp, String card_no, String address);
    boolean updateName(Client client, String name);
    boolean updateICNum(Client client, String icNum);
    boolean updateCNP(Client client, String cnp);
    boolean updateAddress(Client client, String address);
    void removeAll();
}
