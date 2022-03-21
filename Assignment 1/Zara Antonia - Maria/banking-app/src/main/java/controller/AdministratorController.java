package controller;

import model.Activity;
import model.User;
import model.builder.ActivityBuilder;
import model.validator.ActivityValidator;
import model.validator.UserValidator;
import service.activity.ActivityService;
import service.user.AuthenticationService;
import view.AdministratorView;
import view.ReportView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdministratorController {

    private final AdministratorView administratorView;
    private final AuthenticationService authenticationService;
    private final ActivityService activityService;
    private final ActivityValidator activityValidator;
    private final UserValidator userValidator;

    public AdministratorController(AdministratorView administratorView,
                                   AuthenticationService authenticationService,
                                   ActivityService activityService, ActivityValidator activityValidator,
                                   UserValidator userValidator){
        this.authenticationService = authenticationService;
        this.administratorView = administratorView;
        this.activityService = activityService;
        this.activityValidator = activityValidator;
        this.userValidator = userValidator;

        this.administratorView.setEmployeeComboBox(authenticationService.findAllEmployees());
        this.administratorView.addAddEmployeeButtonListener(new AddEmployeeActionListener());
        this.administratorView.addViewEmployeeButtonListener(new ViewEmployeeActionListener());
        this.administratorView.addDeleteEmployeeButtonListener(new DeleteEmployeeActionListener());
        this.administratorView.addUpdateEmployeeButtonListener(new UpdateEmployeeActionListener());
        this.administratorView.addGenerateReportsEmployeeButtonListener(new GenerateReportsEmployeeActionListener());
    }

    private class AddEmployeeActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = administratorView.getUsername();
            String password = administratorView.getPassword();

            String error = userValidator.getEmailValidationError(username);

            if(error.equals("")) {
                if(authenticationService.register(username,password)){
                    JOptionPane.showMessageDialog(null,"Registered succesfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Registered unsuccessfully");
                }
            } else {
                JOptionPane.showMessageDialog(null, error);
            }

            administratorView.setEmployeeComboBox(authenticationService.findAllEmployees());
        }
    }

    private class ViewEmployeeActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            User user = administratorView.getSelectedEmployee();

            administratorView.setUsername(user.getUsername());
            administratorView.setPassword(user.getPassword());
        }
    }

    private class UpdateEmployeeActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = administratorView.getUsername();
            String password = administratorView.getPassword();

            userValidator.validate(username,password);

            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty()) {
                User user = administratorView.getSelectedEmployee();
                authenticationService.update(user, username, password);
            } else {
            JOptionPane.showMessageDialog(null, userValidator.getFormattedErrors());
            }

            administratorView.setEmployeeComboBox(authenticationService.findAllEmployees());
        }
    }

    private class DeleteEmployeeActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(authenticationService.delete(administratorView.getSelectedEmployee())){
                JOptionPane.showMessageDialog(null,"Deleted successfully");
            } else{
                JOptionPane.showMessageDialog(null,"Delete unsuccesful");
            }
            administratorView.setEmployeeComboBox(authenticationService.findAllEmployees());
        }
    }

    private class GenerateReportsEmployeeActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String startDate = administratorView.getStartDate();
            String endDate = administratorView.getEndDate();

            String error = activityValidator.getValidateDateError(startDate);
            String error2 = activityValidator.getValidateDateError(endDate);

            if(error.equals("") && error2.equals("")) {
                User user = administratorView.getSelectedEmployee();

                ArrayList<Activity> activities = activityService.findAllByEmployee(user);
                ArrayList<Activity> filteredActivities = new ArrayList<>();
                for (Activity a : activities) {
                    if (a.getDate().after(Date.valueOf(startDate)) && a.getDate().before(Date.valueOf(endDate))) {
                        filteredActivities.add(a);
                    }
                }

                StringBuilder sb = new StringBuilder("");
                for (Activity a : filteredActivities) {
                    sb.append(a.getDescription() + "\n");
                }

                ReportView reportView = new ReportView();
                reportView.setReport(sb.toString());
            }
            else {
                JOptionPane.showMessageDialog(null, error + "\n" + error2);
            }
        }
    }
}
