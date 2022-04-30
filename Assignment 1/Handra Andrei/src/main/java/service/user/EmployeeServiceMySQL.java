package service.user;

import database.Constants;
import model.Account;
import model.Client;
import model.Right;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import repository.security.RightsRolesRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceMySQL implements EmployeeService{

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final CurrentSession currentSession;

    public EmployeeServiceMySQL(ClientRepository clientRepository, AccountRepository accountRepository, RightsRolesRepository rightsRolesRepository, CurrentSession currentSession) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.rightsRolesRepository = rightsRolesRepository;
        this.currentSession = currentSession;
    }

    @Override
    public boolean addNewClient(String name, String cnp, String cardNumber, String address) {
        Client client = new ClientBuilder()
                .setName(name)
                .setCNP(cnp)
                .setCardNumber(cardNumber)
                .setAddress(address)
                .build();
        Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.ADD_CLIENT);
        rightsRolesRepository.addAction(right,currentSession.getCurrentUser());
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.VIEW_CLIENT);
        rightsRolesRepository.addAction(right,currentSession.getCurrentUser());
        List<Client> clients = clientRepository.findAll();
        return clients;
    }

    @Override
    public boolean updateClient(Long id, String name, String cnp, String cardNumber, String address) {
        Optional<Client> client = clientRepository.findById(id);

        if(client.isPresent()) {
            if (!name.isEmpty()) {
                client.get().setName(name);
            }
            if(!cnp.isEmpty()){
                client.get().setCnp(cnp);
            }
            if(!cardNumber.isEmpty()){
                client.get().setCardNumber(cardNumber);
            }
            if(!address.isEmpty()){
                client.get().setAddress(address);
            }
            Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.UPDATE_CLIENT);
            rightsRolesRepository.addAction(right,currentSession.getCurrentUser());
            return clientRepository.update(client.get());
        }
        return false;
    }

    @Override
    public boolean createAccount(Long id, String cardNumber, String type, Float sumOfMoney) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            Account account = new AccountBuilder()
                    .setIdentificationNumber(id)
                    .setCardNumber(cardNumber)
                    .setType(type)
                    .setSumOfMoney(sumOfMoney)
                    .setCreationDate(new Date())
                    .build();
            Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.CREATE_ACCOUNT);
            rightsRolesRepository.addAction(right,currentSession.getCurrentUser());
            return accountRepository.save(account);
        }
        return false;
    }

    @Override
    public List<Account> findAllAccounts() {
        Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.VIEW_ACCOUNT);
        rightsRolesRepository.addAction(right,currentSession.getCurrentUser());
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    @Override
    public boolean updateAccount(String cardNumber, String newCardNumber, String type, Float sumOfMoney) {
        Optional<Account> account = accountRepository.findAccountByCardNumber(cardNumber);
        if(account.isPresent()){
            if(!newCardNumber.isEmpty()){
                account.get().setCardNumber(newCardNumber);
            }
            if(!type.isEmpty()){
                account.get().setType(type);
            }
            if(sumOfMoney != -1){
                account.get().setSumOfMoney(sumOfMoney);
            }
            Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.UPDATE_ACCOUNT);
            rightsRolesRepository.addAction(right,currentSession.getCurrentUser());
            return accountRepository.update(account.get(),cardNumber);
        }
        return false;
    }

    @Override
    public boolean deleteAccount(String cardNumber) {
        Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.DELETE_ACCOUNT);
        rightsRolesRepository.addAction(right,currentSession.getCurrentUser());
        return accountRepository.delete(cardNumber);
    }

    @Override
    public boolean processBill(String cardNumber, Float amount) {
        Optional<Account> account = accountRepository.findAccountByCardNumber(cardNumber);
        if(account.isPresent()){
            Float initialSum = account.get().getSumOfMoney();
            if(initialSum - amount < 0){
                return false;
            }else{
                account.get().setSumOfMoney(initialSum-amount);
            }
            Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.PROCESS_BILLS);
            rightsRolesRepository.addAction(right,currentSession.getCurrentUser());
            return accountRepository.update(account.get(),account.get().getCardNumber());
        }
        return false;
    }

    @Override
    public boolean transferMoney(String senderCard, String receiverCardNumber, Float sumToBeTransferred) {
        Optional<Account> senderAccount = accountRepository.findAccountByCardNumber(senderCard);
        Optional<Account> receiverAccount = accountRepository.findAccountByCardNumber(receiverCardNumber);
        if(senderAccount.isPresent() && receiverAccount.isPresent()){
             Float senderSum = senderAccount.get().getSumOfMoney();
             if(senderSum - sumToBeTransferred < 0){
                 return false;
             }
             senderAccount.get().setSumOfMoney(senderSum- sumToBeTransferred);
             receiverAccount.get().setSumOfMoney(receiverAccount.get().getSumOfMoney() + sumToBeTransferred);
            Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.TRANSFER_MONEY);
            rightsRolesRepository.addAction(right,currentSession.getCurrentUser());
             return (accountRepository.update(senderAccount.get(), senderCard) && accountRepository.update(receiverAccount.get(), receiverCardNumber));
        }
        return false;
    }
}
