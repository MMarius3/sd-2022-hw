package controller;

import model.Account;
import model.Client;
import model.validator.AccountValidator;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import view.AccountView;
import view.BillView;
import view.NewAccountView;
import view.TransferView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class AccountController {
    private final AccountView accountView;
    private final AccountRepository accountRepository;
    private final NewAccountView newAccountView;
    private final ClientRepository clientRepository;
    private final AccountService accountService;
    private final BillView billView;
    private final TransferView transferView;
    private final AccountValidator accountValidator;

    public AccountController(AccountView accountView, AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountView = accountView;
        this.billView = new BillView();
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        newAccountView= new NewAccountView();
        transferView= new TransferView();
        accountService=new AccountServiceMySQL(accountRepository);
        accountValidator=new AccountValidator(clientRepository, accountRepository);

        this.accountView.addNewAccountBtnListener(new NewAccountBtnListener());
        this.accountView.searchAccountBtnListener(new SearchAccountBtnListener());
        this.accountView.deleteAccountBtnListener(new DeleteAccountBtnListener());
        this.accountView.updateBtnListener(new UpdateAccountBtnListener());
        this.newAccountView.saveAccountBtnListener(new SaveAccountBtnListener());
        this.accountView.payBillBtnListener(new PayBillBtnListener());
        this.accountView.transferBtnListener(new TransferBtnListener());
        this.billView.payBillBtnListener(new PayCustomerBillBtnListener());
        this.transferView.transferAmountListener(new TransferAmountBtnListener());
    }
    private class NewAccountBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            newAccountView.setVisible(true);
        }
    }
    private class SearchAccountBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long clientid=accountView.getClientId();
            Account account = accountRepository.findByClientId(clientid);
            if (account==null){
                JOptionPane.showMessageDialog(accountView.getContentPane(), "There is no account for this client");
            }else {
                Client client=clientRepository.findById(account.getClient_id());
                accountView.setAccountData(account.getId().toString(), account.getType(), account.getBalance(),
                        client.getName(), account.getCreated_at().toString());
            }
        }
    }
    private class DeleteAccountBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long account_id=Long.parseLong(accountView.getAccountId());
            Account account = accountRepository.findById(account_id);
            if (account==null){
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Account not found with this id");
            }else {
                accountRepository.removeAccount(account_id);
                accountView.setAccountData(null, null, null, null, null);
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Account deleted");

            }
        }
    }
    private class SaveAccountBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountType= newAccountView.getAccountType();
            Long clientId= Long.parseLong(newAccountView.getClientId());

            Long accountBalance=Long.parseLong(newAccountView.getAccountBalance());
            Client client= clientRepository.findById(clientId);
            if(client==null){
                JOptionPane.showMessageDialog(newAccountView.getContentPane(), "No customer with id "+clientId );

            }else {
                accountValidator.validate(accountType, accountBalance, clientId);
                final List<String> errors = accountValidator.getErrors();
                if (errors.isEmpty()) {
                    accountService.register(clientId, accountBalance, accountType);
                    JOptionPane.showMessageDialog(newAccountView.getContentPane(), "Account saved");

                } else {
                    JOptionPane.showMessageDialog(newAccountView.getContentPane(), accountValidator.getFormattedErrors());
                }
            }
        }
    }
    private class PayBillBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           billView.setVisible(true);
        }
    }
    private class TransferBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           transferView.setVisible(true);
        }
    }
    private class PayCustomerBillBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
          String billPirce= billView.getBillPrice();
          String accountId=billView.getAccountId();
          Account account=accountRepository.findById(Long.parseLong(accountId));
          long new_balance=account.getBalance()-Long.parseLong(billPirce);
          if(new_balance>=0){
              accountRepository.updateAccount(account.getId(),account.getType(),new_balance);
              JOptionPane.showMessageDialog(billView.getContentPane(), "Payed successfulyy");

          }else{
              JOptionPane.showMessageDialog(newAccountView.getContentPane(), "Account balance too low");
          }
        }
    }
    private class TransferAmountBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
          Long toTransfer= Long.parseLong(transferView.getTransferedAmount());
          Long accountFromId=Long.parseLong(transferView.getAccountFromId());
          Long accountToId=Long.parseLong(transferView.getAccountToId());
          Account accountTo=accountRepository.findById(accountToId);
          Account accountFrom=accountRepository.findById(accountFromId);
          if(toTransfer>accountFrom.getBalance()){
              JOptionPane.showMessageDialog(transferView.getContentPane(), "Account balance too low");
          }else{
              accountRepository.updateAccount(accountFrom.getId(),accountFrom.getType(),accountFrom.getBalance()-toTransfer);
              accountRepository.updateAccount(accountTo.getId(),accountTo.getType(),accountTo.getBalance()+toTransfer);
              JOptionPane.showMessageDialog(transferView.getContentPane(), "Transfer successful");

          }

        }
    }

    private class UpdateAccountBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long new_balance= Long.valueOf(accountView.getAccountBalance());
            String account_type= accountView.getAccountType();
            Long account_id= Long.valueOf(accountView.getAccountId());
            Long clientId=accountView.getClientId();
            accountValidator.validateForUpdate(account_type,new_balance,clientId);
            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                accountRepository.updateAccount(account_id,account_type,new_balance);
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Update successful");

            } else {
                JOptionPane.showMessageDialog(newAccountView.getContentPane(), accountValidator.getFormattedErrors());
            }
        }
    }

}
