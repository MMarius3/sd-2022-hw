package model.builder;

import model.Client;
import model.ClientAccount;

import java.sql.Date;

public class ClientAccountBuilder {

    private ClientAccount clientAccount;

    public ClientAccountBuilder() {
        this.clientAccount = new ClientAccount();
    }

    public ClientAccountBuilder(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
    }

    public ClientAccountBuilder setIdentificationNumber(Integer id){
        clientAccount.setIdentificationNumber(id);
        return this;
    }

    public ClientAccountBuilder setType(String type){
        clientAccount.setType(type);
        return this;
    }

    public ClientAccountBuilder setAmount(Double amount){
        clientAccount.setAmount(amount);
        return this;
    }

    public ClientAccountBuilder setOwner(Client client){
        clientAccount.setOwner(client);
        return this;
    }

    public ClientAccountBuilder setCreationDate(Date date){
        clientAccount.setCreationDate(date);
        return this;
    }

    public ClientAccount build(){
        return clientAccount;
    }
}
