package service.user;

import model.Account;
import model.Client;

import java.util.List;

public interface EmployeeService {

    boolean addNewClient(String name, String cnp, String cardNumber, String address);

    List<Client> findAll();

    boolean updateClient(Long id,String name, String cnp, String cardNumber, String address);

    boolean createAccount(Long id, String cardNumber, String type, Float sumOfMoney);

    List<Account> findAllAccounts();

    boolean updateAccount(String cardNumber, String newCardNumber, String type, Float sumOfMoney);

    boolean deleteAccount(String cardNumber);

    boolean processBill(String cardNumber, Float amount);

    boolean transferMoney(String senderCard,String receiverCardNumber,Float sumToBeTransferred);

}
