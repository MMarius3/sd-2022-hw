package controller;

import model.Account;
import model.Client;
import model.validator.ClientAccountValidator;
import model.validator.ClientInformationValidator;
import service.client.ClientService;
import service.client.account.AccountService;
import view.EmployeeView;
import view.client.account.AddAccountView;
import view.client.account.TransferMoneyView;
import view.client.account.UpdateAccountView;
import view.client.information.AddInformationView;
import view.client.information.UpdateInformationView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeController {

    private static final String[] informationTableColumns = new String[]{"ID", "Name", "CNP", "Address"};
    private static final String[] accountTableColumns = new String[]{"ID", "Client", "Number", "Type", "Money", "Creation"};
    private final EmployeeView employeeView;
    private final ClientInformationValidator clientValidator;
    private final ClientAccountValidator accountValidator;
    private final ClientService<Client, Long> clientService;
    private final AccountService accountService;

    public EmployeeController(EmployeeView employeeView,
                              ClientInformationValidator clientValidator,
                              ClientAccountValidator accountValidator,
                              ClientService<Client, Long> clientService,
                              AccountService accountService) {
        this.employeeView = employeeView;
        this.clientValidator = clientValidator;
        this.accountValidator = accountValidator;
        this.clientService = clientService;
        this.accountService = accountService;

        setTableColumns();
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

        employeeView.getInformationView().getBtnViewClientInformation().doClick();
        employeeView.getAccountView().getBtnViewClientAccount().doClick();

    }

    private void setTableColumns() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) employeeView.getInformationView().getClientsIntormationTable().getModel();
        defaultTableModel.setColumnIdentifiers(informationTableColumns);

        DefaultTableModel accountDefaultTableModel = (DefaultTableModel) employeeView.getAccountView().getAccountInformationTable().getModel();
        accountDefaultTableModel.setColumnIdentifiers(accountTableColumns);
    }

    protected void setViewVisible() {
        this.employeeView.getInformationView().setVisible(true);
        this.employeeView.getAccountView().setVisible(true);
    }

    private class AddInformationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddInformationController(new AddInformationView(), clientValidator, clientService);
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
            new UpdateInformationController(new UpdateInformationView(),
                    clientValidator,
                    clientService,
                    employeeView,
                    Long.valueOf(id));
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
            new AddAccountController(new AddAccountView(), accountService, accountValidator);
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
                new UpdateAccountController(new UpdateAccountView(),
                    accountValidator,
                    accountService,
                    employeeView,
                    Long.valueOf(id));
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
            new TransferMoneyController(new TransferMoneyView(), accountService, employeeView, accountValidator);
        }
    }
}
