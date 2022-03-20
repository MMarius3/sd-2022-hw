package controller;

import javafx.scene.control.Button;
import model.Account;
import model.Client;
import model.builder.ClientBuilder;
import service.account.AccountService;
import service.client.ClientService;
import view.ClientView;

import java.util.ArrayList;
import java.util.List;

public class ClientController {

    private final ClientService clientService;
    private final AccountService accountService;
    private final AccountController accountController;
    private final ClientView clientView;

    public ClientController(ClientService clientService, AccountService accountService, AccountController accountController){
        this.clientService = clientService;
        this.accountService = accountService;
        this.accountController = accountController;
        this.clientView = new ClientView();
    }

    public void startController(Client client){
        clientView.initializeFields();
        if (client != null){
            setFields(client);
            initializeAccountsDisplay(client);
            initializeAddAccountButton(client);
        }
        initializeSaveButtonListener(client);
        clientView.display();

    }

    private void setFields(Client client){
        clientView.setNameField(client.getName());
        clientView.setIdNumberField(client.getIdNumber());
        clientView.setPersonalIdentificationCodeField(client.getPersonalNumericalCode().toString());
        clientView.setAddressField(client.getAddress());
    }

    private void initializeSaveButtonListener(Client client){
        clientView.getSaveButton().setOnAction(event ->{

            Client newClient = new ClientBuilder()
                    .setName(clientView.getNameField().getText())
                    .setIdNumber(clientView.getIdNumberField().getText())
                    .setPersonalNumericalCode(Integer.valueOf(clientView.getPersonalIdentificationCodeField().getText()))
                    .setAddress(clientView.getAddressField().getText())
                    .build();
            if (client == null){
                clientService.save(newClient);
            }
            else{
                newClient.setId(client.getId());
                clientService.update(newClient);
            }

        });
    }

    private void initializeAccountsDisplay(Client client){
        List<Account> accounts = new ArrayList<>(accountService.findByClientId(client.getId()));
        List<Button> buttons = new ArrayList<>();
        for(Account account : accounts){
            Button button = new Button(account.getId()+ " " + account.getType().getType());
            button.setOnAction(e ->{
                accountButtonListener(account, client);
            });

            buttons.add(button);
        }
        clientView.initializeScrollPane(buttons);
    }

    private void accountButtonListener(Account account, Client client){
        accountController.startController(account, client);
    }

    private void initializeAddAccountButton(Client client){
        clientView.getAddAccountButton().setOnAction(e -> {
            accountController.startController(null, client);
        });
    }
}
