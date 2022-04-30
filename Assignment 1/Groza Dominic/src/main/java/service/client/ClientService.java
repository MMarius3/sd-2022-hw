package service.client;

import model.User;

public interface ClientService {


    boolean register(String id_card_number, String personal_numerical_code, String address,String name);


}