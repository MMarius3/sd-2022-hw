package controller;

import model.validator.UserValidator;
import service.user.AuthenticationService;
import view.AdministratorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdministratorController {

    private final AdministratorView administratorView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;

    public AdministratorController(AdministratorView administratorView, AuthenticationService authenticationService, UserValidator userValidator) {
        this.administratorView = administratorView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;

        this.administratorView.addAddEmployeeButtonListener(new AddEmployeeButtonListener());
        this.administratorView.addUpdateEmployeeButtonListener(new UpdateEmployeeButtonListener());
        this.administratorView.addViewEmployeeButtonListener(new ViewEmployeeButtonListener());
        this.administratorView.addDeleteEmployeeButtonListener(new DeleteEmployeeButtonListener());
        this.administratorView.addGenerateReportButtonListener(new GenerateReportButtonListener());
        this.administratorView.setVisible(true);
    }

    private class AddEmployeeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = administratorView.getEmployeeName();

            userValidator.validateEmail(name);

            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                authenticationService.addEmployee(name);
            } else {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }

    private class UpdateEmployeeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = administratorView.getEmployeeId();
            String name = administratorView.getEmployeeName();

            userValidator.validateUpdate(idString, name);

            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                Long id = Long.getLong(idString);
                authenticationService.updateEmployee(id, name);
            } else {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }

    private class ViewEmployeeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = administratorView.getEmployeeId();

            userValidator.validateID(idString);

            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                Long id = Long.getLong(idString);
                authenticationService.viewEmployee(id);
            } else {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }

    private class DeleteEmployeeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = administratorView.getEmployeeId();

            userValidator.validateID(idString);

            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                Long id = Long.getLong(idString);
                authenticationService.deleteEmployee(id);
            } else {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }

    private class GenerateReportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = administratorView.getEmployeeId();

            userValidator.validateID(idString);

            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                Long id = Long.getLong(idString);
                authenticationService.generateReport(id);
            } else {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }
}
