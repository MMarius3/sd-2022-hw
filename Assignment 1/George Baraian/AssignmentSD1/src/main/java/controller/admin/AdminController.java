package controller.admin;

import model.User;
import model.validation.Notification;
import services.admin.AdminService;
import services.user.AuthenticationService;
import view.admin.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminController {
    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final AdminService adminService;

    public AdminController(AdminView adminView, AuthenticationService authenticationService, AdminService adminService) {
        this.adminView = adminView;
        this.authenticationService = authenticationService;
        this.adminService = adminService;
        adminView.setCreateEmployeeButtonListener(new CreateEmployeeButtonListener());
        adminView.setDeleteEmployeeButtonListener(new DeleteEmployeeButtonListener());
        adminView.setViewEmployeesButtonListener(new ViewEmployeesButtonListener());
    }

    private class CreateEmployeeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String employeeUsername = adminView.getUsername();
            String employeePassword = adminView.getPassword();

            Notification<Boolean> createEmployeeNotification = authenticationService.register(employeeUsername,employeePassword);

            if(createEmployeeNotification.hasErrors()){
                JOptionPane.showMessageDialog(adminView.getContentPane(), createEmployeeNotification.getFormattedErrors());
            }else{
                if(!createEmployeeNotification.getResult()){
                    JOptionPane.showMessageDialog(adminView.getContentPane(),"Creating an employee was not successful");
                }
                else{
                    JOptionPane.showMessageDialog(adminView.getContentPane(),"Employee created successfully");
                }
            }

        }
    }


    private class DeleteEmployeeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String employeeUsername = adminView.getUsername();
            Notification<Boolean> deleteEmployeeNotification = adminService.deleteEmployee(employeeUsername);
            if(deleteEmployeeNotification.hasErrors()){
                JOptionPane.showMessageDialog(adminView.getContentPane(),deleteEmployeeNotification.getFormattedErrors());
            }else{
                if(!deleteEmployeeNotification.getResult()){
                    JOptionPane.showMessageDialog(adminView.getContentPane(),"Could not delete employee");
                }
                else{
                    JOptionPane.showMessageDialog(adminView.getContentPane(),"Deleted the employee");
                }
            }
        }
    }

    private class ViewEmployeesButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<User> all = adminService.viewAllEmployees();
            adminView.createResultTable(all);
        }
    }
}
