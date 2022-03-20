package model.builder;

import model.Client;
import java.time.LocalDate;

public class ClientBuilder {

    private Client client;

    public ClientBuilder() {
        client = new Client();
    }

    public ClientBuilder setIdCardNumber(String number) {
        client.setId_card_number(number);
        return this;
    }
    public ClientBuilder setPersonalNumericalCode(String code) {
        client.setPersonal_numerical_code(code);
        return this;
    }
    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }
    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }
    public ClientBuilder setId(Long id){
        client.setId(id);
        return this;
    }

    public ClientBuilder setCreationDate(LocalDate date){
        client.setCreated_at(date);
        return this;
    }

    public Client build() {
        return client;
    }

}