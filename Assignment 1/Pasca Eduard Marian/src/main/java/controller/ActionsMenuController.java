package controller;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import repository.client.ClientRepositoryMySQL;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class ActionsMenuController{

   private final UtilityView2 utilityView2;

   private final Connection connection;

    public ActionsMenuController(UtilityView2 v, Connection connection){
        this.utilityView2 = v;
        this.connection = connection;
        this.utilityView2.addPerformSelectedButtonListener(new performSelectedButtonListener());
    }

    private class performSelectedButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String operation = String.valueOf(utilityView2.getActionComboBox().getSelectedItem());
            System.out.println(operation + " ababababa   ababa   ababa");
            switch (operation) {
                case "Add client":
                    AddClientView newViewAddC = new AddClientView();
                    newViewAddC.setVisible(true);
                    utilityView2.dispose();
                    break;
                case "Add account":
                    AddAccountView newViewAddA = new AddAccountView();
                    newViewAddA.setVisible(true);
                    utilityView2.dispose();
                    break;
                case "Delete account":
                    DeleteAccountView newViewDeleteA = new DeleteAccountView();
                    newViewDeleteA.setVisible(true);
                    utilityView2.dispose();
                    break;
                case "Update client":
                    UpdateClientView newViewUpdateC = new UpdateClientView();
                    newViewUpdateC.setVisible(true);
                    utilityView2.dispose();
                    break;
                case "Update account":
                    UpdateAccountView newViewUpdateA = new UpdateAccountView();
                    newViewUpdateA.setVisible(true);
                    utilityView2.dispose();
                    break;
                case "Transfer money":
                    TransferMoneyView newViewTransfer = new TransferMoneyView();
                    newViewTransfer.setVisible(true);
                    utilityView2.dispose();
                    break;
                case "View clients":
                    break;
                case "View accounts":
                    break;
            }
        }
    }

}
