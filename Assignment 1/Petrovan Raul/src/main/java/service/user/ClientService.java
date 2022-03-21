package service.user;

import model.Client;
import model.User;
import helpers.validation.Notification;

import java.util.List;

public interface ClientService {

    List<Client> fetchClients();

    Notification<List<User>> fetchUsersWithoutAccounts();

    Notification<Client> createClient(Client c);

    Notification<Boolean> updateClient(Client c);

    Notification<Boolean> deleteClient(Long clientId);

    Notification<Boolean> deleteUser(Long id);

    Notification<Boolean> addUserRole(Long id, String role);

    Notification<Boolean> removeUserRole(Long id, String role);
}
