package helpers;

import model.Account;
import model.Client;
import model.Role;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Helpers {
    public static List<Client> removeClientById(List<Client> clients, Long clientId) {
        clients.removeIf(client -> Objects.equals(client.getId(), clientId));
        return clients;
    }

    public static void replaceClient(List<Client> clients, Client selectedClient) {
        for (int i = 0; i < clients.size(); i++) {
            if(Objects.equals(clients.get(i).getId(), selectedClient.getId())) {
                clients.set(i, selectedClient);
            }
        }
    }

    public static List<Account> removeAccountFromClient(Account selectedAccount, Client selectedClient) {
        List<Account> accounts = selectedClient.getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            accounts.removeIf(account -> Objects.equals(account.getId(), selectedAccount.getId()));
        }
        return accounts;
    }

    public static void replaceAccount(List<Account> accounts, Account selectedAccount) {
        for (int i = 0; i < accounts.size(); i++) {
            if (Objects.equals(accounts.get(i).getId(), selectedAccount.getId())) {
                accounts.set(i, selectedAccount);
            }
        }
    }

    public static void addMoneyToAccount(List<Client> clients, String text, float parseFloat) {
        for (Client client : clients) {
            for (int j = 0; j < client.getAccounts().size(); j++) {
                if (client.getAccounts().get(j).getAccountNumber().equals(text)) {
                    client.getAccounts().get(j)
                            .setBalance(client.getAccounts().get(j).getBalance() + parseFloat);
                }
            }
        }
    }

    public static String shortRolesString(List<Role> roles) {
        StringBuilder stringBuilder = new StringBuilder();
        if(roles.isEmpty()) return "[]";
        stringBuilder.append("[");
        for (Role role : roles) {
            stringBuilder.append(role.getRole()).append(", ");
        }
        stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length(), "]");
        return stringBuilder.toString();
    }

    public static List<User> removeUserById(List<User> users, Long id) {
        users.removeIf(user -> Objects.equals(user.getId(), id));
        return users;
    }
}
