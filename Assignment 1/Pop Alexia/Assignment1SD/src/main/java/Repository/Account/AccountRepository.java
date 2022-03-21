package Repository.Account;

import Controller.Response;
import Model.Account;
import javafx.collections.ObservableList;

public interface AccountRepository {

    ObservableList<Account> findAll();

    Account findByCardNr(Long cardnr);

    boolean save(Account account);
    boolean update(Long accnr,String type,int amount);
    boolean delete(Long cardnr);

    void removeAll();

    boolean transfer(Long acc1,Long acc2,int amount);

    boolean payBill(Long clientId,int amount);

    Response<Boolean> existsByCardnr(Long cardnr);
}
