package controller;

import model.Account;
import model.Client;
import service.account.AccountService;
import service.client.ClientService;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ActionsMenuController {

    private final ActionsMenuView actionsMenuView;

    private final ClientService clientService;

    private final AccountService accountService;


    private final AddAccountView addAccountView;
    private final AddClientView addClientView;
    private final DeleteUserView deleteUserView;
    private final DeleteAccountView deleteAccountView;
    private final UpdateClientView updateClientView;
    private final UpdateAccountView updateAccountView;
    private final TransferMoneyView transferMoneyView;

    public ActionsMenuController(ActionsMenuView actionsMenuView,
                                 ClientService clientService,
                                 AccountService accountService,
                                 AddAccountView addAccountView,
                                 AddClientView addClientView,
                                 DeleteUserView deleteUserView,
                                 DeleteAccountView deleteAccountView,
                                 UpdateClientView updateClientView,
                                 UpdateAccountView updateAccountView,
                                 TransferMoneyView transferMoneyView) {
        this.actionsMenuView = actionsMenuView;
        this.clientService = clientService;
        this.accountService = accountService;
        this.addAccountView = addAccountView;
        this.addClientView = addClientView;
        this.deleteUserView = deleteUserView;
        this.deleteAccountView = deleteAccountView;
        this.updateClientView = updateClientView;
        this.updateAccountView = updateAccountView;
        this.transferMoneyView = transferMoneyView;

        this.actionsMenuView.addPerformSelectedButtonListener(new PerformSelectedButtonListener());
    }


    private class PerformSelectedButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String operation = actionsMenuView.getActionComboBox();

            switch (operation) {
                case "Add client":
                    actionsMenuView.dispose();
                    addClientView.setVisible(true);
                    break;
                case "Add account":
                    actionsMenuView.dispose();
                    addAccountView.setVisible(true);
                    break;
                case "Delete user":
                    actionsMenuView.dispose();
                    deleteUserView.setVisible(true);
                    break;
                case "Delete account":
                    actionsMenuView.dispose();
                    deleteAccountView.setVisible(true);
                    break;
                case "Update client":
                    actionsMenuView.dispose();
                    updateClientView.setVisible(true);
                    break;
                case "Update account":
                    actionsMenuView.dispose();
                    updateAccountView.setVisible(true);
                    break;
                case "Transfer money":
                    actionsMenuView.dispose();
                    transferMoneyView.setVisible(true);
                    break;
                case "View clients":
                    List<Client> clientList = clientService.findAll();
                    break;
                case "View accounts":
                    List<Account> accountList = accountService.findAll();
                    break;
            }
        }
    }

}
