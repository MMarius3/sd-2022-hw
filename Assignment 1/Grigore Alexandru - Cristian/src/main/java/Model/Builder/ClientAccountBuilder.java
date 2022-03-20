package Model.Builder;

import Model.ClientAccount;

import java.util.Date;

public class ClientAccountBuilder {

    private ClientAccount clientAccount;

    public ClientAccountBuilder(){
        clientAccount = new ClientAccount();
    }

    public ClientAccountBuilder setId(Long id){
        clientAccount.setId(id);
        return this;
    }

    public ClientAccountBuilder setIdentificationNumber(Long identificationNumber){
        clientAccount.setIdentificationNumber(identificationNumber);
        return this;
    }

    public ClientAccountBuilder setType(String type){
        clientAccount.setType(type);
        return this;
    }

    public ClientAccountBuilder setMoney(int money){
        clientAccount.setMoney(money);
        return this;
    }

    public ClientAccountBuilder setCreationDate(Date creationDate){
        clientAccount.setCreationDate(creationDate);
        return this;
    }

    public ClientAccount build(){
        return clientAccount;
    }

}
