package controller;

import service.account.AccountService;
import view.TransferMoneyView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class TransferMoneyController {
    private final TransferMoneyView transferMoneyView;
    private final Connection connection;
    private final AccountService accountService;

    public TransferMoneyController(TransferMoneyView transferMoneyView, Connection connection, AccountService accountService) {
        this.transferMoneyView = transferMoneyView;
        this.connection = connection;
        this.accountService = accountService;
        this.transferMoneyView.performTransferButtonListener(new TransferMoneyButtonListener());
    }

    private class TransferMoneyButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            long senderId = transferMoneyView.getSenderId();
            long receiverId = transferMoneyView.getReceiverId();
            long amount = transferMoneyView.getAmount();

            accountService.transferMoney(senderId, receiverId, amount);
        }
    }
}
