package controller;

import model.ClientAccount;
import model.User;
import model.builder.ActivityBuilder;
import model.validator.ClientAccountValidator;
import service.activity.ActivityService;
import service.client_account.ClientAccountService;
import view.ProcessUtilitiesView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;

public class ProcessUtilitiesController {

    private final ProcessUtilitiesView processUtilitiesView;
    private final ClientAccountService clientAccountService;
    private final EmployeeController employeeController;
    private final User loggedUser;
    private final ActivityService activityService;
    private final ClientAccountValidator clientAccountValidator;

    public ProcessUtilitiesController(ProcessUtilitiesView processUtilitiesView, ClientAccountService clientAccountService, 
                                      EmployeeController employeeController, User loggedUser, ActivityService activityService,
                                      ClientAccountValidator clientAccountValidator){
        this.processUtilitiesView = processUtilitiesView;
        this.clientAccountService = clientAccountService;
        this.employeeController = employeeController;
        this.activityService = activityService;
        this.loggedUser = loggedUser;
        this.clientAccountValidator = clientAccountValidator;

        this.processUtilitiesView.addProcessUtilitiesButtonListener(new ProcessUtilitiesActionListener());
        this.processUtilitiesView.setClientAccountComboBox(clientAccountService.findAll());
    }

    private class ProcessUtilitiesActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ClientAccount clientAccount = processUtilitiesView.getSelectedClientAccount();
            String amount = processUtilitiesView.getAmount();
            String utility = processUtilitiesView.getSelectedUtility();

            String error = clientAccountValidator.getValidateAmountError(amount);

            if(error.isEmpty()) {
                if (clientAccountService.processUtility(clientAccount, utility, Double.parseDouble(amount))) {
                    JOptionPane.showMessageDialog(null, "Utility pay successful", null, JOptionPane.INFORMATION_MESSAGE);
                    employeeController.setClientAccountComboBox();
                    Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                    activityService.add(new ActivityBuilder()
                            .setDate(date)
                            .setEmployee(loggedUser)
                            .setDescription("Paid Utility Bill")
                            .build());
                } else {
                    JOptionPane.showMessageDialog(null, "Utility pay unsuccessful", null, JOptionPane.WARNING_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null,error);
            }
        }
    }
}
