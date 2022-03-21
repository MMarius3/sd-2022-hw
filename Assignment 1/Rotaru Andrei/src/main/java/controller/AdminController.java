package controller;

import database.Constants;
import model.Activity;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import service.activity.ActivityService;
import service.user.UserService;
import view.AdminView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdminController {
    private final AdminView adminView;
    private final LoginView loginView;

    private final UserService userService;
    private final ActivityService activityService;

    private final TableProcessing tableProcessing;

    public AdminController(AdminView adminView, LoginView loginView, UserService userService,
                           ActivityService activityService, TableProcessing tableProcessing){
        this.adminView = adminView;
        this.loginView = loginView;
        this.userService = userService;
        this.activityService = activityService;
        this.tableProcessing = tableProcessing;
        adminView.setAddUserButtonListener(new AddUserButtonListener());
        adminView.setUpdateUserButtonListener(new UpdateUserButtonListener());
        adminView.setDeleteUserButtonListener(new DeleteUserButtonListener());
        adminView.setViewUserButtonListener(new ViewUserButtonListener());
        adminView.setGenerateReportsButtonListener(new GenerateReportsButtonListener());
        adminView.setLogoutButtonListener(new LogoutButtonListener());
        this.adminView.loadEmployeesTable(tableProcessing.generateTable(userService.findAllEmployees(),
                Constants.Columns.EMPLOYEE_TABLE_COLUMNS));
        this.adminView.loadActivitiesTable(tableProcessing.generateTable(new ArrayList<>(),
                Constants.Columns.ACTIVITY_TABLE_COLUMNS));
    }

    private class AddUserButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();

            Notification<Boolean> addEmployeeNotification = userService.createEmployee(username,password);
            if (addEmployeeNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), addEmployeeNotification.getFormattedErrors());
            } else {
                if (!addEmployeeNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Adding employee not successful!");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Adding employee successful!");
                }
            }
        }
    }

    private class UpdateUserButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();
            Long employeeId = adminView.getSelectedRowFromEmployees();

            Notification<Boolean> updateEmployeeNotification = userService.updateUser(employeeId, username);
            if (updateEmployeeNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), updateEmployeeNotification.getFormattedErrors());
            } else {
                if (!updateEmployeeNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Editing employee not successful!");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Editing employee successful!");
                }
            }
        }
    }

    private class ViewUserButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            adminView.loadEmployeesTable(tableProcessing.generateTable(userService.findAllEmployees(),
                    Constants.Columns.EMPLOYEE_TABLE_COLUMNS));
        }
    }

    private class DeleteUserButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Long employeeId = adminView.getSelectedRowFromEmployees();

            User user = new UserBuilder()
                    .setId(employeeId)
                    .build();

            boolean deleteEmployee = userService.deleteUser(user);
            if(deleteEmployee){
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Deleting employee successful!");
            }
            else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Deleting employee not successful!");
            }
        }
    }

    private class GenerateReportsButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Long employeeId = adminView.getSelectedRowFromEmployees();
            String dateFrom = adminView.getDate1();
            String dateTo = adminView.getDate2();

            List<Activity> activities = activityService.findByDateAndId(employeeId,dateFrom,dateTo);
            if(activities.isEmpty()){
                JOptionPane.showMessageDialog(adminView.getContentPane(),"There are no activities!\n" +
                        "Date must be yyyy-MM-dd!");
                adminView.loadActivitiesTable(tableProcessing.generateTable(new ArrayList<>(),
                        Constants.Columns.ACTIVITY_TABLE_COLUMNS));
            }
            else{
                adminView.loadActivitiesTable(tableProcessing.generateTable(activities,
                        Constants.Columns.ACTIVITY_TABLE_COLUMNS));
            }
        }
    }

    private class LogoutButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            adminView.setVisible(false);
            loginView.setVisible(true);
            loginView.clearTextFields();
        }
    }
}
