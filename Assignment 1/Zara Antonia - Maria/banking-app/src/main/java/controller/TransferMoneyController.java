package controller;

import model.ClientAccount;
import model.User;
import model.builder.ActivityBuilder;
import model.validator.ClientAccountValidator;
import service.activity.ActivityService;
import service.client_account.ClientAccountService;
import view.EmployeeView;
import view.TransferMoneyView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;

public class TransferMoneyController {

    private final TransferMoneyView transferMoneyView;
    private final ClientAccountService clientAccountService;
    private final EmployeeController employeeController;
    private final ActivityService activityService;
    private final User loggedUser;
    private final ClientAccountValidator clientAccountValidator;

    public TransferMoneyController(TransferMoneyView transferMoneyView, EmployeeController employeeController,
                                   ClientAccountService clientAccountService, User loggedUser, ActivityService activityService,
                                   ClientAccountValidator clientAccountValidator){
        this.transferMoneyView = transferMoneyView;
        this.clientAccountService = clientAccountService;
        this.employeeController = employeeController;
        this.activityService = activityService;
        this.loggedUser = loggedUser;
        this.clientAccountValidator = clientAccountValidator;

        this.transferMoneyView.addTransferMoneyButtonListener(new TransferMoneyActionListener());
        this.transferMoneyView.setComboBoxes(clientAccountService.findAll());
    }

    private class TransferMoneyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ClientAccount fromAccount = transferMoneyView.getSelectedAccountFrom();
            ClientAccount toAccount = transferMoneyView.getSelectedAccountTo();
            String amount = transferMoneyView.getAmount();


                String error = clientAccountValidator.getValidateAmountError(amount.toString());

                if(error.isEmpty()) {
                    if (amount.equals("") || amount == null) {
                        JOptionPane.showMessageDialog(null, "Amount empty");
                    } else {

                        if (clientAccountService.transferMoney(fromAccount, toAccount, Double.parseDouble(amount))) {
                            JOptionPane.showMessageDialog(null, "Transfer successful", null, JOptionPane.INFORMATION_MESSAGE);
                            employeeController.setClientAccountComboBox();
                            Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                            activityService.add(new ActivityBuilder()
                                    .setDate(date)
                                    .setEmployee(loggedUser)
                                    .setDescription("Transfered Money")
                                    .build());
                        } else {
                            JOptionPane.showMessageDialog(null, "Transfer unsuccessful", null, JOptionPane.WARNING_MESSAGE);
                        }
                }
                } else {
                    JOptionPane.showMessageDialog(null,error);
                }
        }
    }
}
