package controller.employee.account;

import model.Action;
import model.User;
import model.validator.AccountValidator;
import service.action.ActionService;
import service.client.account.AccountService;
import view.EmployeeView;
import view.client.account.ProcessBillView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static database.Constants.Actions.PROCESS_BILL;

public class ProcessBillController {
    private final ProcessBillView processBillView;
    private final AccountService accountService;
    private final EmployeeView employeeView;
    private final AccountValidator accountValidator;
    private final ActionService actionService;
    private final User user;

    public ProcessBillController(ProcessBillView processBillView,
                                 AccountService accountService,
                                 AccountValidator accountValidator,
                                 EmployeeView employeeView, ActionService actionService, User user) {
        this.processBillView = processBillView;
        this.accountService = accountService;
        this.employeeView = employeeView;
        this.accountValidator = accountValidator;
        this.actionService = actionService;
        this.user = user;

        initializeButtonsListener();
    }

    private void initializeButtonsListener() {
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
                    processBillView.setVisible(false);
                    actionService.save(Action.builder()
                            .user_id(user.getId())
                            .action(PROCESS_BILL)
                            .date(Date.valueOf(LocalDate.now()))
                            .build());
                    employeeView.getAccountView().getBtnViewClientAccount().doClick();
                } else {
                    JOptionPane.showMessageDialog(processBillView.getContentPane(), accountValidator.getFormattedErrors());
                }
            }

        }
    }
}
