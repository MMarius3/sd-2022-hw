package controller;

import model.validator.AccountValidator;
import service.client.account.AccountService;
import view.EmployeeView;
import view.client.account.TransferMoneyView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TransferMoneyController {
    private final TransferMoneyView transferMoneyView;
    private final AccountService accountService;
    private final EmployeeView employeeView;
    private final AccountValidator accountValidator;

    public TransferMoneyController(TransferMoneyView transferMoneyView,
                                   AccountService accountService,
                                   EmployeeView employeeView,
                                   AccountValidator accountValidator) {
        this.transferMoneyView = transferMoneyView;
        this.accountService = accountService;
        this.employeeView = employeeView;
        this.accountValidator = accountValidator;

        this.transferMoneyView.setCancelButtonActionListener(new CancelButtonListener());
        this.transferMoneyView.setTransferMoneyButtonActionListener(new TransferMoneyButtonListener());
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            transferMoneyView.setVisible(false);
        }
    }

    private class TransferMoneyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fromAccountId = transferMoneyView.getFromAccountField().getText();
            String toAccountId = transferMoneyView.getToAccountField().getText();
            String money = transferMoneyView.getMoneyAmountField().getText();

            accountValidator.validate(fromAccountId, toAccountId, money);
            List<String> errors = accountValidator.getErrors();

            if(errors.isEmpty()) {
                boolean firstFlag = accountService.transferMoney(Long.parseLong(fromAccountId), (-1) * Integer.parseInt(money));
                boolean secondFlag = accountService.transferMoney(Long.parseLong(toAccountId), Integer.parseInt(money));
                if(firstFlag && secondFlag) {
                    JOptionPane.showMessageDialog(transferMoneyView.getContentPane(), "Transfer successful");
                    employeeView.getAccountView().getBtnViewClientAccount().doClick();
                    transferMoneyView.setVisible(false);
                }
            } else {
                JOptionPane.showMessageDialog(transferMoneyView.getContentPane(), accountValidator.getFormattedErrors());
            }
        }
    }
}
