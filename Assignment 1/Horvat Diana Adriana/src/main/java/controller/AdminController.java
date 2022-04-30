package controller;

import model.Activity;
import model.User;
import model.validator.UserValidator;
import service.activity.ActivityService;
import service.user.AuthenticationService;
import service.user.UserService;
import view.admin.AdminAddEmployeeView;
import view.admin.AdminIndexView;
import view.admin.AdminUpdateEmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdminController {

    private final AdminIndexView adminIndexView;
    private final AdminAddEmployeeView adminAddEmployeeView;
    private final AdminUpdateEmployeeView adminUpdateEmployeeView;
    private final UserValidator userValidator;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final ActivityService activityService;

    public AdminController(UserService userService, AuthenticationService authenticationService, UserValidator userValidator, AdminIndexView adminIndexView, AdminAddEmployeeView adminAddEmployeeView,
                           AdminUpdateEmployeeView adminUpdateEmployeeView, ActivityService activityService){
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.adminIndexView = adminIndexView;
        this.adminAddEmployeeView = adminAddEmployeeView;
        this.adminUpdateEmployeeView = adminUpdateEmployeeView;
        this.activityService = activityService;

        this.adminIndexView.addEmployeeViewBtnListener(new AddEmployeeViewBtnListener());
        this.adminIndexView.updateEmployeeViewBtnListener(new UpdateEmployeeViewBtnListener());
        this.adminAddEmployeeView.addEmployeeBtnListener(new AddEmployeeBtnListener());
        this.adminUpdateEmployeeView.searchEmployeeBtnListener(new SearchEmployeeBtnListener());
        this.adminUpdateEmployeeView.updateEmployeeBtnListener(new UpdateEmployeeBtnListener());
        this.adminUpdateEmployeeView.deleteEmployeeBtnListener(new DeleteEmployeeBtnListener());
        this.adminIndexView.generateReportBtnListener(new GenerateReportBtnListener());
    }

    private class AddEmployeeViewBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            adminAddEmployeeView.setVisible(true);
        }
    }

    private class UpdateEmployeeViewBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            adminUpdateEmployeeView.setVisible(true);
        }
    }

    private class AddEmployeeBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = adminAddEmployeeView.getTfUsername();
            String password = adminAddEmployeeView.getTfPassword();

            userValidator.validate(username, password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                authenticationService.register(username, password);
                JOptionPane.showMessageDialog(adminAddEmployeeView.getContentPane(), "Registered successfully");
            } else {
                JOptionPane.showMessageDialog(adminAddEmployeeView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }

    private class SearchEmployeeBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = adminUpdateEmployeeView.getTfOldUsername();
            userValidator.resetErrorsArray();
            userValidator.validateEmail(username);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()){
                User user = userService.findByUsername(username);
                if(user != null && user.getRoles().get(0).getRole().equals("employee")){
                    adminUpdateEmployeeView.setTfUsername(user.getUsername());
                    adminUpdateEmployeeView.setTfPassword(user.getPassword());
                }else{
                    JOptionPane.showMessageDialog(adminUpdateEmployeeView.getContentPane(), "This user does not exist or is an admin");
                }
            }else{
                JOptionPane.showMessageDialog(adminUpdateEmployeeView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }

    private class UpdateEmployeeBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String oldUsername = adminUpdateEmployeeView.getTfOldUsername();
            userValidator.resetErrorsArray();
            userValidator.validateEmail(oldUsername);
            List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                User user = userService.findByUsername(oldUsername);
                if (user != null && user.getRoles().get(0).getRole().equals("employee")) {
                    String username = adminUpdateEmployeeView.getTfUsername();
                    String password = adminUpdateEmployeeView.getTfPassword();

                    userValidator.resetErrorsArray();
                    if(!user.getPassword().equals(password) && !user.getUsername().equals(username)){
                        userValidator.validate(username, password);
                    }
                    else if(!user.getUsername().equals(username)){
                        userValidator.validateEmailUniqueness(username);
                        userValidator.validateEmail(username);
                    }
                    else if(!user.getPassword().equals(password)){
                        userValidator.validatePasswordDigit(password);
                        userValidator.validatePasswordLength(password);
                        userValidator.validatePasswordSpecial(password);
                    }

                    errors = userValidator.getErrors();
                    if (errors.isEmpty()) {

                        user.setUsername(username);

                        if(!user.getPassword().equals(password)){
                            String passwordEncoded = authenticationService.encodePassword(password);
                            user.setPassword(passwordEncoded);
                        }else{
                            user.setPassword(password);
                        }

                        if (userService.updateUser(user)) {
                            JOptionPane.showMessageDialog(adminUpdateEmployeeView.getContentPane(), "Employee updated");
                        } else {
                            JOptionPane.showMessageDialog(adminUpdateEmployeeView.getContentPane(), "Something went wrong");
                        }
                    }else{
                        JOptionPane.showMessageDialog(adminUpdateEmployeeView.getContentPane(), userValidator.getFormattedErrors());
                    }
                } else {
                    JOptionPane.showMessageDialog(adminUpdateEmployeeView.getContentPane(), "This user does not exist or is an admin");
                }
            } else {
                JOptionPane.showMessageDialog(adminUpdateEmployeeView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }
    private class DeleteEmployeeBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String oldUsername = adminUpdateEmployeeView.getTfOldUsername();
            userValidator.resetErrorsArray();
            userValidator.validateEmail(oldUsername);
            List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()){
                User user = userService.findByUsername(oldUsername);
                if(user != null && user.getRoles().get(0).getRole().equals("employee")){
                    if(userService.removeUser(user)){
                        JOptionPane.showMessageDialog(adminUpdateEmployeeView.getContentPane(), "Employee deleted");
                    }else{
                        JOptionPane.showMessageDialog(adminUpdateEmployeeView.getContentPane(), "Something went wrong");
                    }
                }else{
                    JOptionPane.showMessageDialog(adminUpdateEmployeeView.getContentPane(), "This user does not exist or is an admin");
                }
            }else{
                JOptionPane.showMessageDialog(adminUpdateEmployeeView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }

    private class GenerateReportBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String employee_id = adminIndexView.getEmployee_id();

            if(userValidator.validateId(employee_id)){
                User user = userService.findById(Integer.parseInt(employee_id));
                if(user != null){
                    List<Activity> activities = activityService.getActivitiesForUser(user);
                    adminIndexView.displayActivities(activities);

                }else{
                    JOptionPane.showMessageDialog(adminUpdateEmployeeView.getContentPane(), "Employee does not exist");
                }
            }else{
                JOptionPane.showMessageDialog(adminUpdateEmployeeView.getContentPane(), "Employee id not valid");
            }

        }
    }
}

