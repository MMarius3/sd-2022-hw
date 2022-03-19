package Service.Client;

import javafx.collections.ObservableList;

public interface ClientService {


    boolean addClient(String name, Long cardnr,Long pnc,String address);
    ObservableList viewClients();
    boolean updateClient(Long id , String name,Long pnc,String address);
}
