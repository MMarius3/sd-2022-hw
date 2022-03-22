package controller;

import model.Account;
import model.Client;
import service.account.AccountServiceDisplay;
import service.client.ClientServiceDisplay;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class ActionsMenuController{

   private final ActionsMenuView actionsMenuView;

    private final ClientServiceDisplay clientServiceDisplay;

   private final AccountServiceDisplay accountServiceDisplay;

   private final Connection connection;

    public ActionsMenuController(ActionsMenuView v, Connection connection, ClientServiceDisplay clientServiceDisplay, AccountServiceDisplay accountServiceDisplay){
        this.actionsMenuView = v;
        this.clientServiceDisplay = clientServiceDisplay;
        this.connection = connection;
        this.accountServiceDisplay = accountServiceDisplay;
        System.out.println("aaaaaaaa");
        this.actionsMenuView.addPerformSelectedButtonListener(new PerformSelectedButtonListener());
    }

    private class PerformSelectedButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println(" adad  dadadad   dadada   dada");
            String operation = actionsMenuView.getActionComboBox();

            switch (operation) {
                case "Add client":
                    AddClientView newViewAddC = new AddClientView();
                    newViewAddC.setVisible(true);
                    actionsMenuView.dispose();
                    break;
                case "Add account":
                    AddAccountView newViewAddA = new AddAccountView();
                    newViewAddA.setVisible(true);
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
                    List <Client> clientList = clientServiceDisplay.findAll();
                    break;
                case "View accounts":
                    List<Account> accountList = accountServiceDisplay.findAll();
                    break;
            }
        }
    }

}
