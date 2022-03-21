package controller;

import model.Role;
import model.User;
import model.validator.UserValidator;
import repository.user.UserRepository;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.NewEmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminController {
    private final AdminView adminView;
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final NewEmployeeView newEmployeeView;
    private final AuthenticationService authenticationService;


    public AdminController(AdminView adminView, UserRepository userRepository, UserValidator userValidator
            , AuthenticationService authenticationService) {
        this.adminView = adminView;
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.authenticationService = authenticationService;
//        this.adminView.setVisible(true);
        this.newEmployeeView = new NewEmployeeView();

        this.adminView.deleteEmployeeBtnListener(new DeleteEmployeeBtnListener());
        this.adminView.createEmployeeBtnListener(new CreateEmployeeBtnListener());
        this.adminView.updateEmployeeBtnListener(new UpdateEmployeeBtnListener());
        this.adminView.searchEmployeeBtnListener(new SearchEmployeeBtnListener());
        this.newEmployeeView.submitBtnListener(new SubmitBtnListener());
    }

    private class DeleteEmployeeBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long employeeId = adminView.getEmployeeId();
            User employee = userRepository.findById(employeeId);
            if (employee == null) {
                JOptionPane.showMessageDialog(newEmployeeView.getContentPane(), "Employee with id " + employeeId + " not found");
            } else {
                userRepository.removeUser(employeeId);
                JOptionPane.showMessageDialog(newEmployeeView.getContentPane(), "Delete successful");
            }
        }
    }

    private class CreateEmployeeBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            newEmployeeView.setVisible(true);
        }
    }

    private class UpdateEmployeeBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long employeeId = adminView.getEmployeeId();
            User employee = userRepository.findById(employeeId);
            if (employee == null) {
                JOptionPane.showMessageDialog(newEmployeeView.getContentPane(), "Employee with id " + employeeId + " not found");
            } else {
                String employeeUsername = adminView.getEmployeeUsername();
                userValidator.validateForUpdate(employeeUsername);
                final List<String> errors = userValidator.getErrors();
                if (errors.isEmpty()) {
                    userRepository.updateUser(employeeId, employeeUsername);
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Update successful");

                } else {
                    JOptionPane.showMessageDialog(newEmployeeView.getContentPane(), userValidator.getFormattedErrors());
                }
            }
        }
    }

    private class SearchEmployeeBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long employeeId = adminView.getEmployeeId();
            User employee = userRepository.findById(employeeId);
            if (employee == null) {
                JOptionPane.showMessageDialog(newEmployeeView.getContentPane(), "Employee with id " + employeeId + " not found");
            } else {
                adminView.setEmployeeData(employee.getUsername(), employee.getPassword(), employeeId);
            }
        }
    }

    private class SubmitBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String employeeUsername = newEmployeeView.getUsername();
            String employeePassword = newEmployeeView.getPassword();
            userValidator.validate(employeeUsername, employeePassword);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                authenticationService.register(employeeUsername, employeePassword);
                JOptionPane.showMessageDialog(newEmployeeView.getContentPane(), "Register successful");

            } else {
                JOptionPane.showMessageDialog(newEmployeeView.getContentPane(), userValidator.getFormattedErrors());
            }

        }
    }

}
