package Service.Account;

import Model.Account;
import javafx.collections.ObservableList;


public interface AccountService {

    boolean addAccount(Long cardnr, String type, int amount);
    boolean deleteAccount(Long accNr);
    ObservableList<Account> viewAccounts();
    boolean updateAccount(Long cardnr, String type, int amount);
    boolean transferMoney(Long acc1,Long acc2,int amount);
    boolean payBill(Long accNr,int amount);

}
