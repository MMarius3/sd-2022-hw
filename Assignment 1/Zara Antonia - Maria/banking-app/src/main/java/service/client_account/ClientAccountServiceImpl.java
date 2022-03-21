package service.client_account;

import model.ClientAccount;
import repository.client_account.ClientAccountRepository;

import java.util.ArrayList;
import java.util.Optional;

public class ClientAccountServiceImpl implements ClientAccountService {

    private ClientAccountRepository clientAccountRepository;

    public ClientAccountServiceImpl(ClientAccountRepository clientAccountRepository) {
        this.clientAccountRepository = clientAccountRepository;
    }

    @Override
    public ArrayList<ClientAccount> findAll() {
        return clientAccountRepository.findAll();
    }

    @Override
    public boolean add(ClientAccount clientAccount) {
        return clientAccountRepository.add(clientAccount, clientAccount.getOwner());
    }

    @Override
    public boolean update(ClientAccount clientAccount, String type, Optional<Double> amount) {
        ClientAccount updatedClientAccount = new ClientAccount();

        updatedClientAccount.setOwner(clientAccount.getOwner());
        updatedClientAccount.setCreationDate(clientAccount.getCreationDate());
        updatedClientAccount.setIdentificationNumber(clientAccount.getIdentificationNumber());

        if(type == null){
            updatedClientAccount.setType(clientAccount.getType());
        }
        else{
            updatedClientAccount.setType(type);
        }

        if(amount.isEmpty()){
            updatedClientAccount.setAmount(clientAccount.getAmount());
        }
        else{
            updatedClientAccount.setAmount(amount.get());
        }

        return clientAccountRepository.update(updatedClientAccount);
    }

    @Override
    public boolean delete(ClientAccount clientAccount) {
        return clientAccountRepository.delete(clientAccount);
    }

    @Override
    public boolean transferMoney(ClientAccount fromAccount, ClientAccount toAccount, Double amount) {
        if(fromAccount.getAmount() < amount){
            return  false;
        }
        update(fromAccount, fromAccount.getType(), Optional.of(fromAccount.getAmount() - amount));
        update(toAccount, toAccount.getType(), Optional.of(toAccount.getAmount() + amount));
        return true;
    }

    @Override
    public boolean processUtility(ClientAccount clientAccount, String utility, Double amount) {
        if(clientAccount.getAmount() < amount){
            return false;
        }
        update(clientAccount, clientAccount.getType(), Optional.of(clientAccount.getAmount() - amount));
        return true;
    }
}
