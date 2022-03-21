package controller.employee;

import controller.employee.account.AddAccountController;
import controller.employee.account.ProcessBillController;
import controller.employee.account.TransferMoneyController;
import controller.employee.account.UpdateAccountController;
import controller.employee.information.AddInformationController;
import controller.employee.information.UpdateInformationController;
import model.Account;
import model.Action;
import model.Client;
import model.User;
import model.validator.AccountValidator;
import model.validator.ClientInformationValidator;
import service.action.ActionService;
import service.client.ClientService;
import service.client.account.AccountService;
import view.EmployeeView;
import view.client.account.ActionAccountView;
import view.client.account.ProcessBillView;
import view.client.account.TransferMoneyView;
import view.client.information.ActionInformationView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static database.Constants.Actions.*;

public class EmployeeController {

    private static final String[] informationTableColumns = new String[]{"ID", "Name", "CNP", "Address"};
    private static final String[] accountTableColumns = new String[]{"ID", "Client", "Number", "Type", "Money", "Creation"};
    private final EmployeeView employeeView;
    private final ClientInformationValidator clientValidator;
    private final AccountValidator accountValidator;
    private final ClientService<Client, Long> clientService;
    private final AccountService accountService;
    private final ActionService actionService;
    private User user;

    public EmployeeController(EmployeeView employeeView,
                              ClientInformationValidator clientValidator,
                              AccountValidator accountValidator,
                              ClientService<Client, Long> clientService,
                              AccountService accountService, ActionService actionService) {
        this.employeeView = employeeView;
        this.clientValidator = clientValidator;
        this.accountValidator = accountValidator;
        this.clientService = clientService;
        this.accountService = accountService;
        this.actionService = actionService;

        setTablesHeader();
        initializeButtonsListener();
    }

    private void initializeButtonsListener() {
        employeeView.getInformationView().setAddClientInformationListener(new AddInformationButtonListener());
        employeeView.getInformationView().setUpdateClientInformationListener(new UpdateInformationButtonListener());
        employeeView.getInformationView().setViewClientInformationListener(new ViewInformationButtonListener());

        employeeView.getAccountView().setAddClientAccountListener(new AddAccountButtonListener());
        employeeView.getAccountView().setUpdateClientAccountListener(new UpdateAccountButtonListener());
        employeeView.getAccountView().setDeleteClientAccountListener(new DeleteAccountButtonListener());
        employeeView.getAccountView().setViewClientAccountListener(new ViewAccountButtonListener());

        employeeView.getAccountView().setTransferAccountListener(new TransferButtonListener());
        employeeView.getAccountView().setProcessBillListener(new ProcessBillButtonListener());

        employeeView.getInformationView().getBtnViewClientInformation().doClick();
        employeeView.getAccountView().getBtnViewClientAccount().doClick();

    }

    private void setTablesHeader() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) employeeView.getInformationView().getClientsIntormationTable().getModel();
        defaultTableModel.setColumnIdentifiers(informationTableColumns);

        DefaultTableModel accountDefaultTableModel = (DefaultTableModel) employeeView.getAccountView().getAccountInformationTable().getModel();
        accountDefaultTableModel.setColumnIdentifiers(accountTableColumns);
    }

    public void setViewVisible() {
        this.employeeView.getInformationView().setVisible(true);
        this.employeeView.getAccountView().setVisible(true);
    }

    public void setUser(User user) {
        this.user = user;
    }

    private class AddInformationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddInformationController(new ActionInformationView(), clientValidator, clientService, employeeView, actionService, user);
        }
    }

    private class UpdateInformationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!isOneClientSelected()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(),
                        "Please select one client");
                return;
            }

            int selectedRow = employeeView.getInformationView().getClientsIntormationTable().getSelectedRow();
            String id = (String) employeeView.getInformationView().getClientsIntormationTable().getValueAt(selectedRow, 0);
            new UpdateInformationController(new ActionInformationView(),
                    clientValidator,
                    clientService,
                    employeeView,
                    actionService,
                    user, Long.valueOf(id));
        }

        private boolean isOneClientSelected() {
            return employeeView.getInformationView().getClientsIntormationTable().getSelectedRows().length == 1;
        }
    }

    private class ViewInformationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Client> clients = clientService.findall();
            DefaultTableModel defaultTableModel = (DefaultTableModel) employeeView.getInformationView().getClientsIntormationTable().getModel();
            defaultTableModel.setRowCount(0);
            for(Client client: clients) {
                final String[] data = new String[]{String.valueOf(client.getId()),
                        client.getName(),
                        client.getCNP(),
                        client.getAddress()};
                defaultTableModel.addRow(data);
            }
        }
    }

    private class AddAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddAccountController(new ActionAccountView(), accountService, accountValidator, employeeView, actionService, user);
        }
    }

    private class UpdateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!isOneAccountSelected()) {
                JOptionPane.showMessageDialog(employeeView.getAccountView().getContentPane(),
                        "Please select one account");
                return;
            }

            int selectedRow = employeeView.getAccountView().getAccountInformationTable().getSelectedRow();
            String id = (String) employeeView.getAccountView().getAccountInformationTable().getValueAt(selectedRow, 0);
                new UpdateAccountController(new ActionAccountView(), accountValidator, accountService,
                        employeeView, actionService, user, Long.valueOf(id));
        }

        private boolean isOneAccountSelected() {
            return employeeView.getAccountView().getAccountInformationTable().getSelectedRows().length == 1;
        }
    }

    private class DeleteAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!isOneAccountSelected()) {
                JOptionPane.showMessageDialog(employeeView.getAccountView().getContentPane(),
                        "Please select one account");
                return;
            }
            int row = employeeView.getAccountView().getAccountInformationTable().getSelectedRow();
            Long id = Long.parseLong((String) employeeView.getAccountView().getAccountInformationTable().getValueAt(row, 0));
            System.out.println(id);
            boolean flag = accountService.delete(id);
            if(flag) {
                JOptionPane.showMessageDialog(employeeView.getAccountView().getContentPane(), "" +
                        "Account with id " + id + " has been deleted");
                Action action = Action.builder()
                        .user_id(user.getId())
                        .action(DELETE_ACCOUNT)
                        .date(Date.valueOf(LocalDate.now()))
                        .build();
                actionService.save(action);
                employeeView.getAccountView().getBtnViewClientAccount().doClick();
            }
        }

        private boolean isOneAccountSelected() {
            return employeeView.getAccountView().getAccountInformationTable().getSelectedRows().length == 1;
        }
    }

    private class ViewAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Account> accounts = accountService.findall();
            DefaultTableModel defaultTableModel = (DefaultTableModel) employeeView.getAccountView().getAccountInformationTable().getModel();
            defaultTableModel.setRowCount(0);
            for(Account account: accounts) {
                final String[] data = new String[]{String.valueOf(account.getId()),
                    String.valueOf(account.getClient_id()),
                    account.getNumber(),
                    account.getType(),
                    String.valueOf(account.getMoney()),
                    String.valueOf(account.getDate())};
                defaultTableModel.addRow(data);
            }
        }
    }

    private class TransferButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new TransferMoneyController(new TransferMoneyView(), accountService, employeeView, accountValidator, actionService, user);
        }
    }

    private class ProcessBillButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ProcessBillController(new ProcessBillView(), accountService, accountValidator, employeeView, actionService, user);
        }
    }
}
