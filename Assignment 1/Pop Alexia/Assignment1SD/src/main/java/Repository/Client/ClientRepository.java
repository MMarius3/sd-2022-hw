package Repository.Client;

import Controller.Response;
import Model.Client;
import javafx.collections.ObservableList;

public interface ClientRepository {

    ObservableList<Client> findAll();

    Client findByCardNr(Long cardnr);
    Client findById(Long id);

    boolean save(Client client);
    boolean update(Long id , String name,Long pnc,String address);

    void removeAll();

    Response<Boolean> existsByCardnr(Long cardNr);
    public Response<Boolean> existsByPNC(Long pnc);


}
