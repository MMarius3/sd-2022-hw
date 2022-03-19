package controller;

import model.validator.ClientAccountValidator;
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
    private final ClientAccountValidator clientAccountValidator;

    public TransferMoneyController(TransferMoneyView transferMoneyView,
                                   AccountService accountService,
                                   EmployeeView employeeView,
                                   ClientAccountValidator clientAccountValidator) {
        this.transferMoneyView = transferMoneyView;
        this.accountService = accountService;
        this.employeeView = employeeView;
        this.clientAccountValidator = clientAccountValidator;

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

            clientAccountValidator.validate(fromAccountId, toAccountId, money);
            List<String> errors = clientAccountValidator.getErrors();

            if(errors.isEmpty()) {
                boolean firstFlag = accountService.transferMoney(Long.parseLong(fromAccountId), (-1) * Integer.parseInt(money));
                boolean secondFlag = accountService.transferMoney(Long.parseLong(toAccountId), Integer.parseInt(money));
                employeeView.getAccountView().getBtnViewClientAccount().doClick();
                if(firstFlag && secondFlag) {
                    JOptionPane.showMessageDialog(transferMoneyView.getContentPane(), "Transfer successful");
                    transferMoneyView.setVisible(false);
                }
            } else {
                JOptionPane.showMessageDialog(transferMoneyView.getContentPane(), clientAccountValidator.getFormattedErrors());
            }
        }
    }
}
