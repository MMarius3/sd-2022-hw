package presentation_layer.controllers;

import bussiness_layer.builder.AccountBuilder;
import bussiness_layer.builder.ActionBuilder;
import bussiness_layer.builder.UserBuilder;
import bussiness_layer.models.Account;
import bussiness_layer.models.Action;
import bussiness_layer.models.User;
import bussiness_layer.service.employee.EmployeeService;
import presentation_layer.view.EmployeeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeView employeeView, EmployeeService employeeService) {
        this.employeeView = employeeView;
        this.employeeService = employeeService;
        this.employeeView.addViewClientButtonListener(new ViewClientButtonListener());
        this.employeeView.addViewAccountsButtonListener(new ViewAccountsButtonListener());
        this.employeeView.addNewClientButtonListener(new AddNewClientButtonListener());
        this.employeeView.addUpdateClientButtonListener(new AddUpdateClientButtonListener());
        this.employeeView.addUpdateAccountButtonListener(new AddUpdateAccountButtonListener());
        this.employeeView.addDeleteAccountButtonListener(new AddDeleteAccountButtonListener());
        this.employeeView.addInsertAccountButtonListener(new AddInsertAccountButtonListener());
        this.employeeView.addProcessBillsButtonListener(new AddProcessBillsButtonListener());
        this.employeeView.addTransferMoneyButtonListener(new AddTransferMoneyButtonListener());
        this.employeeView.setVisible(false);
    }

    private class ViewClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<User> customers = employeeService.getAllCustomers();

            String[] columnNames = new String[] {"Username", "Password", "Id_Series", "Id_number", "Pnc", "Address"};
            String[][] data = new String[customers.size()][6];

            for (int i = 0; i< customers.size(); i++)
            {
                System.out.println(customers.get(i).getUsername() + " " + customers.get(i).getPassword() + " "+ customers.get(i).getId_series()+ " "+customers.get(i).getId_number().toString()+" "+ customers.get(i).getPnc()+" "+customers.get(i).getAddress());
                data[i][0] = customers.get(i).getUsername();
                data[i][1] = customers.get(i).getPassword();
                data[i][2] = customers.get(i).getId_series();
                data[i][3] = customers.get(i).getId_number().toString();
                data[i][4] = customers.get(i).getPnc();
                data[i][5] = customers.get(i).getAddress();
            }

            employeeView.addViewClientsTable(data,columnNames);
            Action action = new ActionBuilder()
                    .setType("View clients")
                    .setDate(new Date(System.currentTimeMillis()))
                    .build();
            employeeService.insertAction(action);
        }
    }

    private class ViewAccountsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = employeeView.getTfUsername1Client();
            List<Account> accounts = employeeService.getClientAccount(username);

            String[] columnNames = new String[] {"Id", "Type", "Amount_Of_Money", "Date_Of_Creation"};
            String[][] data = new String[accounts.size()][4];

            for (int i = 0; i< accounts.size(); i++)
            {
                System.out.println(accounts.get(i).getType()+" "+ accounts.get(i).getType()+ " "+ accounts.get(i).getAmount_of_money().toString()+ " " + accounts.get(i).getDate_of_creation().toString());
                data[i][0] = accounts.get(i).getId().toString();
                data[i][1] = accounts.get(i).getType();
                data[i][2] = accounts.get(i).getAmount_of_money().toString();
                data[i][3] = accounts.get(i).getDate_of_creation().toString();
            }

            employeeView.addViewAccountsTable(data,columnNames);
            Action action = new ActionBuilder()
                    .setType("View accounts")
                    .setDate(new Date(System.currentTimeMillis()))
                    .build();
            employeeService.insertAction(action);
        }
    }

    private class AddNewClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = employeeView.getTfUsername1Client();
            String password = employeeView.getTfPassword1Client();
            String id_series = employeeView.getTfId_Series1Client();
            String id_number = employeeView.getTfId_Number1Client();
            String pnc = employeeView.getTfPnc1Client();
            String address = employeeView.getTfAddress1Client();

            User newClient = new UserBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setId_series(id_series)
                    .setId_number(Long.parseLong(id_number) )
                    .setPnc(pnc)
                    .setAddress(address)
                    .build();
           boolean verify =  employeeService.addCustomer(newClient);
            Action action = new ActionBuilder()
                    .setType("Add new client with username " + username)
                    .setDate(new Date(System.currentTimeMillis()))
                    .build();
            employeeService.insertAction(action);
        }
    }

    private class AddUpdateClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String usernameInitial = employeeView.getTfUsername1Client();

            User initialUser = employeeService.findByUsername(usernameInitial);

            String username = employeeView.getTfUsername2Client();
            if (username.equals(""))
            {
                username = initialUser.getUsername();
            }

            String password = initialUser.getPassword();

            String id_series = employeeView.getTfId_Series2Client();
            if(id_series.equals(""))
            {
                id_series = initialUser.getId_series();
            }

            String id_number = employeeView.getTfId_Number2Client();
            if (id_number.equals(""))
            {
                id_number = initialUser.getId_number().toString();
            }

            String pnc = employeeView.getTfPnc2Client();
            if (pnc.equals(""))
            {
                pnc = initialUser.getPnc();
            }

            String address = employeeView.getTfAddress2Client();
            if (address.equals(""))
            {
                address = initialUser.getAddress();
            }

            User updatedClient = new UserBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setId_series(id_series)
                    .setId_number(Long.parseLong(id_number) )
                    .setPnc(pnc)
                    .setAddress(address)
                    .build();

            boolean verify =  employeeService.updateCustomer(usernameInitial, updatedClient);
            Action action = new ActionBuilder()
                    .setType("Update client with username "+ usernameInitial)
                    .setDate(new Date(System.currentTimeMillis()))
                    .build();
            employeeService.insertAction(action);
        }
    }

    private class AddUpdateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idInitial = employeeView.getTfId1Account();

            Account initialAccount = employeeService.getById(Long.parseLong(idInitial));

            String id = employeeView.getTfId2Account();
            if (id.equals(""))
            {
                id = initialAccount.getId().toString();
            }
            String type = employeeView.getTfType2Account();
            if (type.equals(""))
            {
                type = initialAccount.getType().toString();
            }
            String amount_of_money = employeeView.getTfMoney2Account();
            if (amount_of_money.equals(""))
            {
                amount_of_money = initialAccount.getAmount_of_money().toString();
            }

            Account newAccount = new AccountBuilder()
                    .setId(Long.parseLong(id))
                    .setType(type)
                    .setAmountOfMoney(Float.parseFloat(amount_of_money))
                    .setDateOfCreation(initialAccount.getDate_of_creation())
                    .build();
            boolean verify =  employeeService.updateAccount(initialAccount.getId(), newAccount);
            Action action = new ActionBuilder()
                    .setType("Update account with id "+ idInitial)
                    .setDate(new Date(System.currentTimeMillis()))
                    .build();
            employeeService.insertAction(action);
        }
    }

    private class AddDeleteAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = employeeView.getTfId1Account();
            boolean verify =  employeeService.deleteAccount(Long.parseLong(id));
            Action action = new ActionBuilder()
                    .setType("Delete account with id "+ id)
                    .setDate(new Date(System.currentTimeMillis()))
                    .build();
            employeeService.insertAction(action);
        }
    }

    private class AddProcessBillsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = employeeView.getTfId1Account();
            String amount = employeeView.getTfMoney1Account();

            employeeService.payBills(Long.parseLong(id), Float.parseFloat(amount));
            Action action = new ActionBuilder()
                    .setType("Process bills")
                    .setDate(new Date(System.currentTimeMillis()))
                    .build();
            employeeService.insertAction(action);
        }
    }

    private class AddTransferMoneyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idSource = employeeView.getTfId1Account();
            String idDestination = employeeView.getTfId2Account();
            String amount = employeeView.getTfMoney1Account();

            employeeService.moveMoney(Long.parseLong(idSource), Long.parseLong(idDestination), Float.parseFloat(amount));
            Action action = new ActionBuilder()
                    .setType("Transfer " + amount + " from "+ idSource + " to "+ idDestination)
                    .setDate(new Date(System.currentTimeMillis()))
                    .build();
            employeeService.insertAction(action);
        }
    }

    private class AddInsertAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = employeeView.getTfUsername1Client();

            String type = employeeView.getTfType1Account();
            String amount_of_money = employeeView.getTfMoney1Account();

            Account account = new AccountBuilder()
                    .setType(type)
                    .setAmountOfMoney(Float.parseFloat(amount_of_money))
                    .build();
            boolean verify =  employeeService.insertAccount(username, account);
            Action action = new ActionBuilder()
                    .setType("Create a new account for user " + username)
                    .setDate(new Date(System.currentTimeMillis()))
                    .build();
            employeeService.insertAction(action);
        }
    }
}
