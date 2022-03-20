package controller.employee.account;

import model.Action;
import model.User;
import model.validator.AccountValidator;
import service.action.ActionService;
import service.client.account.AccountService;
import view.EmployeeView;
import view.client.account.TransferMoneyView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static database.Constants.Actions.TRANFER_MONEY;
public class TransferMoneyController {
    private final TransferMoneyView transferMoneyView;
    private final AccountService accountService;
    private final EmployeeView employeeView;
    private final AccountValidator accountValidator;
    private final ActionService actionService;
    private final User user;

    public TransferMoneyController(TransferMoneyView transferMoneyView,
                                   AccountService accountService,
                                   EmployeeView employeeView,
                                   AccountValidator accountValidator, ActionService actionService, User user) {
        this.transferMoneyView = transferMoneyView;
        this.accountService = accountService;
        this.employeeView = employeeView;
        this.accountValidator = accountValidator;
        this.actionService = actionService;
        this.user = user;

        initializeButtonsListener();
    }

    private void initializeButtonsListener() {
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
                    transferMoneyView.setVisible(false);
                    actionService.save(Action.builder()
                                    .user_id(user.getId())
                                    .action(TRANFER_MONEY)
                                    .date(Date.valueOf(LocalDate.now()))
                            .build());
                    employeeView.getAccountView().getBtnViewClientAccount().doClick();
                }
            } else {
                JOptionPane.showMessageDialog(transferMoneyView.getContentPane(), accountValidator.getFormattedErrors());
            }
        }
    }
}
