package controller.admin;

import model.validation.Notification;
import services.user.AuthenticationService;
import view.admin.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController {
    private final AdminView adminView;
    private final AuthenticationService authenticationService;

    public AdminController(AdminView adminView, AuthenticationService authenticationService) {
        this.adminView = adminView;
        this.authenticationService = authenticationService;
        adminView.setCreateEmployeeButtonListener(new CreateEmployeeButtonListener());
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
}
