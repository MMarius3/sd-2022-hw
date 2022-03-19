package controller.employee;

import model.validator.AccountValidator;
import service.action.ActionService;
import service.client.account.AccountService;
import view.EmployeeView;
import view.client.account.ProcessBillView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProcessBillController {
    private final ProcessBillView processBillView;
    private final AccountService accountService;
    private final EmployeeView employeeView;
    private final AccountValidator accountValidator;

    public ProcessBillController(ProcessBillView processBillView,
                                 AccountService accountService,
                                 AccountValidator accountValidator,
                                 EmployeeView employeeView, ActionService actionService) {
        this.processBillView = processBillView;
        this.accountService = accountService;
        this.employeeView = employeeView;
        this.accountValidator = accountValidator;

        this.processBillView.setCancelAddInformationListener(new CancelButtonListener());
        this.processBillView.setProcessBillButtonListener(new ProcessBillListener());
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            processBillView.setVisible(false);
        }
    }

    private class ProcessBillListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountId = processBillView.getAccountIdField().getText();
            String money = processBillView.getMoneyField().getText();

            accountValidator.validate(accountId, money);

            List<String> errors = accountValidator.getErrors();

            if(errors.isEmpty()) {
                boolean flag = accountService.transferMoney(Long.parseLong(accountId), (-1) * Integer.parseInt(money));

                if(flag) {
                    JOptionPane.showMessageDialog(processBillView.getContentPane(), "Process bill successful");
                    employeeView.getAccountView().getBtnViewClientAccount().doClick();
                    processBillView.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(processBillView.getContentPane(), accountValidator.getFormattedErrors());
                }
            }

        }
    }
}
