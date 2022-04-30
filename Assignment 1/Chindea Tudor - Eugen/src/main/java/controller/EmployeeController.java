package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validator.ClientValidator;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import view.BillsView;
import view.EmployeeView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final BillsView billsView;
    private final ClientValidator clientValidator;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    public EmployeeController(EmployeeView employeeView, ClientValidator clientValidator,ClientRepository clientRepository, AccountRepository accountRepository, BillsView billsView){
        this.clientValidator = clientValidator;
        this.employeeView = employeeView;
        this.billsView = billsView;
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.employeeView.addAddButtonListener(new AddClientButtonListener());
        this.employeeView.addUpdateButtonListener(new UpdateClientButtonListener());
        this.employeeView.addViewButtonListener(new ViewClientButtonListener());
        this.employeeView.addAddButtonListenerAcc(new AddAccButtonListener());
        this.employeeView.addUpdateButtonListenerAcc(new UpdateAccButtonListener());
        this.employeeView.addViewButtonListenerAcc(new ViewAccButtonListener());
        this.employeeView.addDeleteButtonListenerAcc(new DeleteAccButtonListener());
        this.employeeView.addButonBillsListener(new BillsViewLsitener());
        this.billsView.addPayBillButtonListener(new PayBillButonListener());
        this.billsView.addTransferButtonListener(new TransferButonListener());
    }
    private class PayBillButonListener implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Long idFrom  = Long.valueOf(billsView.getIdFrom());
            Long amount = Long.valueOf(billsView.getAmount());
            accountRepository.payBill(idFrom,amount);
        }
    }
private class TransferButonListener implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Long idFrom  = Long.valueOf(billsView.getIdFrom());
            Long amount = Long.valueOf(billsView.getAmount());
            Long idTo = Long.valueOf(billsView.getIdTo());
            accountRepository.transferMoney(idFrom,idTo,amount);
        }
    }

    private class AddClientButtonListener implements ActionListener{
        @Override
        public  void actionPerformed(ActionEvent e){
            String name = employeeView.getName();
            String idCardNumber = employeeView.getidCard();
            String CNP = employeeView.getCnp();
            String address = employeeView.getAddress();
            clientValidator.validate(name,CNP);
            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                ClientBuilder client = new ClientBuilder();
                client.setAddress(address)
                .setCNP(CNP)
                .setName(name)
                .setIdentityCardNumber(idCardNumber)
                .setAddress(address);
                clientRepository.addClient(client.build());
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientValidator.getFormattedErrors());
            }
        }
    }
    private class AddAccButtonListener implements ActionListener{
        @Override
        public  void actionPerformed(ActionEvent e){
            //Long id = Long.valueOf(employeeView.getAccId());
            String type = employeeView.getAccType();
            Long balance = Long.valueOf(employeeView.getAccBalance());
            Long client_id = Long.valueOf(employeeView.getAccClientID());
            LocalDate creationDate= LocalDate.now();

                AccountBuilder account = new AccountBuilder();
                Account newAccount = account.setType(type).setBallance(balance).setClientId(client_id).setCreationdate(creationDate).build();
                accountRepository.addAccount(newAccount);

        }
    }
    private class UpdateClientButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String CNP = employeeView.getCnp();
            String address = employeeView.getAddress();
            clientRepository.updateClientAddress(CNP,address);
        }
    }
    private class UpdateAccButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String id = employeeView.getAccId();
            String balance = employeeView.getAccBalance();
            accountRepository.updateAccountBallance(Long.valueOf(id),Long.valueOf(balance));
        }
    }
    private class ViewClientButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String CNP = employeeView.getCnp();
            Client client = clientRepository.findClientByCnp(CNP);
            employeeView.setAddress(client.getAddress());
            employeeView.setId(client.getId());
            employeeView.setName(client.getName());
            employeeView.setidCard(client.getIdentityCardNumber());
        }
    }
    private class ViewAccButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String id = employeeView.getAccId();
            Account account = accountRepository.findAccountById(Long.valueOf(id));
            employeeView.setAccType(account.getType());
            employeeView.setAccBalance(String.valueOf(account.getBalance()));
            employeeView.setAccClientID(String.valueOf(account.getClient_id()));
            employeeView.setAccDate(String.valueOf(account.getCreation()));
        }
    }
    private class DeleteAccButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String id = employeeView.getAccId();
            accountRepository.deleteAccount(Long.valueOf(id));
        }
    }
    private class BillsViewLsitener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setVisible(false);
            billsView.setVisible(true);
        }
    }
}
