package presentation.controller;

import model.Account;
import model.Client;
import model.User;
import presentation.ui_action_listeners.menu.general_purpose.ProcessUtilitiesActionListener;
import repository.AccountRepository;
import repository.ClientRepository;
import repository.UserRepository;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Menu {

    private presentation.view.Menu menu;

    public Menu() {
        menu = new presentation.view.Menu();

//        menu.setClientsList(getClientList());
//        menu.setClientsAccountsList(getClientsAccountsList());
//        menu.setUsersList(getUsersList());
        this.addListeners();

        menu.setVisible(true);
    }

    private void addListeners() {
        System.out.println("Adding listeners to menu");
        menu.setActionListenerToProcessUtilities(new ProcessUtilitiesActionListener(this));
    }

    private HashMap<Integer, String> getClientList() {

        ClientRepository clientRepository = new ClientRepository();
        List<Optional<Client>> clientList = clientRepository.getAll();

        HashMap<Integer, String> clients = new HashMap<>();
        clientList.forEach(client -> client.ifPresent(value -> clients.put(value.getId(), value.getName())));

        return clients;
    }

    private HashMap<Integer, String> getClientsAccountsList() {

        AccountRepository accountRepository = new AccountRepository();
        List<Optional<Account>> clientsAccountsList = accountRepository.getAll();

        HashMap<Integer, String> accounts = new HashMap<>();
        clientsAccountsList.forEach(account -> account.ifPresent(value -> accounts.put(value.getId(), value.getIdentificationNumber())));

        return accounts;
    }

    private HashMap<Integer, String> getUsersList() {

        UserRepository userRepository = new UserRepository();
        List<Optional<User>> usersList = userRepository.getAll();

        HashMap<Integer, String> users = new HashMap<>();
        usersList.forEach(user -> user.ifPresent(value -> users.put(value.getId(), value.getUsername())));

        return users;
    }

    // --------------------------------------------------

    public String getProcessUtilitiesSum() {
        return menu.getProcessUtilitiesSum();
    }

    public static void main(String[] args) {
        new Menu();
    }
}
