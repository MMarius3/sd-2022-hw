package model.builder;

import model.Account;
import model.Client;
import model.Role;
import model.User;

import java.util.List;

public class ClientBuilder {
    private Client client;

    public ClientBuilder() {
        client = new Client();
    }

    public ClientBuilder setUser(User user) {
        client.setUser(user);
        return this;
    }

    public ClientBuilder setAccounts(List<Account> accounts) {
        client.setAccounts(accounts);
        return this;
    }

    public ClientBuilder setId(Long id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }
    public ClientBuilder setIdNumber(String idNumber) {
        client.setIdNumber(idNumber);
        return this;
    }
    public ClientBuilder setCNP(String CNP) {
        client.setCNP(CNP);
        return this;
    }
    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }



    public Client build() {
        return client;
    }
}
