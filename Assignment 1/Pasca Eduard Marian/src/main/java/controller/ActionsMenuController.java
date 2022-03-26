package controller;

import model.Account;
import model.Client;
import service.account.AccountService;
import service.client.ClientService;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class ActionsMenuController{

   private final ActionsMenuView actionsMenuView;

    private final ClientService clientService;

   private final AccountService accountService;

   private final Connection connection;

    public ActionsMenuController(ActionsMenuView v, Connection connection, ClientService clientService, AccountService accountService){
        this.actionsMenuView = v;
        this.clientService = clientService;
        this.connection = connection;
        this.accountService = accountService;
        this.actionsMenuView.addPerformSelectedButtonListener(new PerformSelectedButtonListener());
    }

    private class PerformSelectedButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String operation = actionsMenuView.getActionComboBox();

            switch (operation) {
                case "Add client":
                    AddClientView newViewAddC = new AddClientView();
                    actionsMenuView.dispose();
                    new AddClientController(newViewAddC, connection);
                    break;
                case "Add account":
                    AddAccountView newViewAddA = new AddAccountView();
                    actionsMenuView.dispose();
                    new AddAccountController(newViewAddA, connection);
                    break;
                case "Delete user":
                    DeleteUserView newViewDeleteU = new DeleteUserView();
                    newViewDeleteU.setVisible(true);
                    actionsMenuView.dispose();
                    break;
                case "Delete account":
                    DeleteAccountView newViewDeleteA = new DeleteAccountView();
                    newViewDeleteA.setVisible(true);
                    actionsMenuView.dispose();
                    break;
                case "Update client":
                    UpdateClientView newViewUpdateC = new UpdateClientView();
                    newViewUpdateC.setVisible(true);
                    actionsMenuView.dispose();
                    break;
                case "Update account":
                    UpdateAccountView newViewUpdateA = new UpdateAccountView();
                    newViewUpdateA.setVisible(true);
                    actionsMenuView.dispose();
                    break;
                case "Transfer money":
                    TransferMoneyView newViewTransfer = new TransferMoneyView();
                    newViewTransfer.setVisible(true);
                    actionsMenuView.dispose();
                    break;
                case "View clients":
                    List <Client> clientList = clientService.findAll();
                    break;
                case "View accounts":
                    List<Account> accountList = accountService.findAll();
                    break;
            }
        }
    }

}
