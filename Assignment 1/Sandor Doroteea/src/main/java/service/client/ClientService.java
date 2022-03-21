package service.client;

import model.Client;

import java.util.List;

public interface ClientService {
    boolean add(String name,String idCardNo,String cnp,String address);
    boolean delete(Long id);
    List<Client> view();
    boolean update(String name,String idCardNo,String cnp,String address,Long id);
}
