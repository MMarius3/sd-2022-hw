package controller;

import model.Account;
import model.builder.AccountBuilder;
import service.account.AccountService;
import view.AccountView;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AccountViewController {

    private final AccountView accountView;
    private final AccountService accountService;
    private final EmployeeView employeeView;

    public AccountViewController(AccountView accountView, AccountService accountService, EmployeeView employeeView) {
        this.accountView = accountView;
        this.accountService = accountService;
        this.employeeView = employeeView;
        accountView.setAddButtonListener(new AddButtonListener());
        accountView.setViewButtonListener(new ViewButtonListener());
        accountView.setUpdateButtonListener(new UpdateButtonListener());
        accountView.setDeleteButtonListener(new DeleteButtonListener());
        accountView.setTransferButtonListener(new TransferButtonListener());
        accountView.setPayButtonListener(new PayButtonListener());
    }
    private class AddButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            Long id_Number = Long.parseLong(accountView.getIdNumber());
            String type = accountView.getTypeAcc();
            int balance = Integer.parseInt(accountView.getBalance());
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String creationDate = formatter.format(date);

            Account newAcc = new AccountBuilder().setIdNumber(id_Number)
                    .setTypeAccount(type)
                    .setAmountOfMoney(balance)
                    .setCreationDate(creationDate).build();
            Long clientId = employeeView.getIdForSelectedClient();
            accountService.addAccount(newAcc,clientId);
        }
    }
    private class ViewButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            long clientId =  employeeView.getIdForSelectedClient();
            ArrayList<Account> accounts = (ArrayList<Account>) accountService.getAccountsForClient(clientId);
            accountView.resetTable();
            accountView.viewAccounts(accounts);
            accountView.resetTextFields();
        }
    }
    private class UpdateButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            long accountId = accountView.getIdForSelectedAccount();
            ArrayList<String> info = accountView.getInfoForSelectClient();
            long idNumber = Long.parseLong(info.get(0));
            String type = info.get(1);
            int balance = Integer.parseInt(info.get(2));
            String creationDate = info.get(3);
            Account newAcc = new AccountBuilder().setId(accountId)
                    .setIdNumber(idNumber)
                    .setTypeAccount(type)
                    .setAmountOfMoney(balance)
                    .setCreationDate(creationDate)
                    .build();
            accountService.updateAccount(newAcc);
        }
    }
    private class DeleteButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            long id = accountView.getIdForSelectedAccount();
            System.out.println(id);
            accountService.deleteAccount(id);
        }
    }

    private class TransferButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            long fromId = accountView.getFromAccountId();
            long toId = accountView.getToAccountId();
            if(fromId == -1 || toId == -1) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select the accounts!");
            }else {
            Account fromAccount = accountService.findAccountById(fromId);
            Account toAccount = accountService.findAccountById(toId);
            int sumToTrasnfer = accountView.getSumToTransfer();
            if(sumToTrasnfer > fromAccount.getAmountOfMoney()){
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Insufficient funds!");
            }else {
            fromAccount.setAmountOfMoney(fromAccount.getAmountOfMoney() - sumToTrasnfer);
            toAccount.setAmountOfMoney(toAccount.getAmountOfMoney() + sumToTrasnfer);
            accountService.updateAccount(fromAccount);
            accountService.updateAccount(toAccount);
            accountView.resetTextFields();}}
        }
    }

    private class PayButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            long id = accountView.getIdForSelectedAccount();
            int sum = accountView.getSumToPay();

            Account account = accountService.findAccountById(id);
            if(sum > account.getAmountOfMoney()){
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Insufficient funds!");
            }else {
                account.setAmountOfMoney(account.getAmountOfMoney() - sum);
                accountService.updateAccount(account);
                accountView.resetTextFields();
                try {
                    FileWriter myWriter = new FileWriter("bill.txt");
                    myWriter.write("The bill has been paid!\n");
                    myWriter.write("Total payment amount: " + sum + "\n");
                    myWriter.write("Actual balance: " + account.getAmountOfMoney());
                    myWriter.close();

                } catch (IOException a) {
                    a.printStackTrace();
                }
            }
        }
    }
}
