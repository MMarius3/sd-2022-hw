package controller;

import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.validation.Notification;
import service.client.ClientService;
import service.user.AuthenticationService;
import view.LoginView;
import view.UserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.function.LongToIntFunction;

public class UserController {
    private final UserView userView;
    //private final AuthenticationService authenticationService;
    //private final User currentUser;
    private final ClientService clientService;

    public UserController(UserView userView, ClientService clientService){//},User currentUser){//, AuthenticationService authenticationService, User currentUser) {
        this.userView = userView;
        //this.authenticationService = authenticationService;
        //this.currentUser = currentUser;
        this.clientService = clientService;
        userView.setBtnInfoViewListener(new UserController.BtnInfoViewListener());
        userView.setBtnInfoUpdateListener(new UserController.BtnInfoUpdateListener());
        userView.setBtnAccountCreateListener(new UserController.BtnAccountCreateListener());
        userView.setBtnAccountUpdateListener(new UserController.BtnAccountUpdateListener());
        userView.setBtnAccountDeleteListener(new UserController.BtnAccountDeleteListener());
        userView.setBtnAccountViewListener(new UserController.BtnAccountViewListener());
        userView.setBtnPayBillListener(new UserController.BtnPayBillListener());
        userView.setBtnTransferListener(new UserController.BtnTransferListener());

    }

    private class BtnInfoViewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = userView.getTfInfoName();
            if(name.compareTo("") != 0) {
                Client client = clientService.findByNameInfo(name);
                userView.getLabelInfo().setText(//"Id:" + client.getId() + "  " +
                        "Name:" + client.getName() + "  " +
                                "CardID:" + client.getCardID() + "  " +
                                "CNP:" + client.getCNP() + "  " +
                                "Address:" + client.getAddress() + "  "
                        //"Balance:" + client.getBalance() + "  " +
                        //"Type:" + client.getType() + "  " +
                        //"Date of creation:" + client.getDateOfCreation().toString()
                );
            }
        }
    }

    public class BtnInfoUpdateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = userView.getTfInfoName();
            Long cardID = Long.parseLong(userView.getTfInfoCardId());
            String CNP = userView.getTfInfoCNP();
            String address = userView.getTfInfoAddress();
            if(name.compareTo("")!=0 && cardID!=null && CNP.compareTo("")!=0 && address.compareTo("")!=0) {
                clientService.updateInfo(name, cardID, CNP, address);
            }
        }
    }

    public class BtnAccountCreateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = userView.getTfInfoName();
            Long cardID = Long.parseLong(userView.getTfInfoCardId());
            String CNP = userView.getTfInfoCNP();
            String address = userView.getTfInfoAddress();
            String type = userView.getTfAccountType();
            int balance = Integer.parseInt(userView.getTfAccountAmmount());
            Date date = new Date();
            Client client = new ClientBuilder()
                    .setName(name)
                    .setCardID(cardID)
                    .setCNP(CNP)
                    .setAddress(address)
                    .setBalance(balance)
                    .setType(type)
                    .setDateOfCreation(date)
                    .build();
            clientService.create(client);
        }
    }

    public class BtnAccountUpdateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //String name = userView.getTfInfoName();
            //Long cardID = Long.parseLong(userView.getTfInfoCardId());
            //String CNP = userView.getTfInfoCNP();
            //String address = userView.getTfInfoAddress();
            String type = userView.getTfAccountType();
            int balance = Integer.parseInt(userView.getTfAccountAmmount());
            Date date = new Date();
            Long id = Long.parseLong(userView.getTfAccountId());
            Client client = new ClientBuilder()
                    //.setName(name)
                    //.setCardID(cardID)
                    //.setCNP(CNP)
                    //.setAddress(address)
                    .setBalance(balance)
                    .setType(type)
                    .setDateOfCreation(date)
                    .setID(id)
                    .build();
            clientService.updateAccount(client);
        }
    }

    public class BtnAccountDeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.parseLong(userView.getTfAccountId());
            clientService.remove(id);
        }
    }

    public class BtnAccountViewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.parseLong(userView.getTfAccountId());
            if(id!=null) {
                Client client = clientService.findByID(id);
                userView.getLabelAccount().setText("Id:" + client.getId() + "  " +
                        "Name:" + client.getName() + "  " +
                        "Balance:" + client.getBalance() + "  " +
                        "Type:" + client.getType() + "  " +
                        "Date of creation:" + client.getDateOfCreation().toString()
                );
            }
        }
    }

    public class BtnPayBillListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = userView.getTfName1();
            Client client = clientService.findByNameInfo(name);
            int balance = client.getBalance()-Integer.parseInt(userView.getTfBill());
            clientService.updateBalance(name,balance);
            userView.getLabelOperations().setText("Payed a bill of " + userView.getTfBill());
        }
    }

    public class BtnTransferListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name1 = userView.getTfName1();
            Client client1 = clientService.findByNameInfo(name1);
            String name2 = userView.getTfName2();
            Client client2 = clientService.findByNameInfo(name2);
            int balance1 = client1.getBalance()-Integer.parseInt(userView.getTfBill());
            int balance2 = client2.getBalance()+Integer.parseInt(userView.getTfBill());
            clientService.updateTransfer(name1,name2,balance1,balance2);
            userView.getLabelOperations().setText("Transfered " + userView.getTfBill() + " from " + name1 + " to " + name2);
        }
    }
}
