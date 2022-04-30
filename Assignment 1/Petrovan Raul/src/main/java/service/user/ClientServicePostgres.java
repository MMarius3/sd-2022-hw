package service.user;

import helpers.validation.ClientValidator;
import model.Client;
import model.User;
import helpers.validation.Notification;
import repository.user.UserRepository;

import java.util.List;

public class ClientServicePostgres implements ClientService{
    private final UserRepository userRepository;

    public ClientServicePostgres(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Client> fetchClients() {
        return userRepository.findAllClients();
    }

    @Override
    public Notification<List<User>> fetchUsersWithoutAccounts() {
        return userRepository.findUsersWithoutAccount();
    }

    @Override
    public Notification<Client> createClient(Client c) {
        ClientValidator clientValidator = new ClientValidator(c);
        boolean clientValid = clientValidator.validate();
        Notification<Client> clientAddNotification = new Notification<>();

        if(!clientValid) {
            clientValidator.getErrors().forEach(clientAddNotification::addError);
        } else {
            return userRepository.createClient(c);
        }
        return clientAddNotification;
    }

    @Override
    public Notification<Boolean> updateClient(Client c) {
        ClientValidator clientValidator = new ClientValidator(c);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientUpdateNotification = new Notification<>();

        if(!clientValid) {
            clientValidator.getErrors().forEach(clientUpdateNotification::addError);
            clientUpdateNotification.setResult(Boolean.FALSE);
        } else {
            return userRepository.updateClient(c);
        }
        return clientUpdateNotification;
    }

    @Override
    public Notification<Boolean> deleteClient(Long clientId) {
        return userRepository.deleteClientById(clientId);
    }

    @Override
    public Notification<Boolean> deleteUser(Long id) {
        return userRepository.deleteUserById(id);
    }

    @Override
    public Notification<Boolean> addUserRole(Long id, String role) {
        return userRepository.addRole(id, role);
    }

    @Override
    public Notification<Boolean> removeUserRole(Long id, String role) {
        return userRepository.removeRole(id, role);
    }
}
