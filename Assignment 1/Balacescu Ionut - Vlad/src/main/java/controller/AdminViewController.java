package controller;

import model.ActionEmployee;
import model.User;
import model.builder.UserBuilder;
import model.validator.UserValidator;
import service.action.ActionService;
import service.employee.EmployeeService;
import view.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminViewController {

    private final AdminView adminView;
    private final EmployeeService employeeService;
    private final UserValidator userValidator;
    private final ActionService actionService;

    public AdminViewController(AdminView adminView, EmployeeService employeeService, UserValidator userValidator, ActionService actionService) {
        this.adminView = adminView;
        this.employeeService = employeeService;
        this.userValidator = userValidator;
        this.actionService = actionService;
        adminView.setAddButtonListener(new AddButtonListener());
        adminView.setViewButtonListener(new ViewButtonListener());
        adminView.setUpdateButtonListener(new UpdateButtonListener());
        adminView.setDeleteButtonListener(new DeleteButtonListener());
        adminView.setShowButtonListener(new ShowReportsButtonListener());
    }
    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            User newEmpl = new UserBuilder().setUsername(username).setPassword(password).build();

            userValidator.validate(newEmpl.getUsername(), newEmpl.getPassword());
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                employeeService.addEmployee(newEmpl);
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Client added!");
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), userValidator.getFormattedErrors());
            }
            adminView.resetTextFields();
        }
    }
    private class ViewButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            adminView.resetTable();
            List<User> allEmployees = employeeService.findAllEmployees();
            adminView.viewAccounts((ArrayList<User>) allEmployees);
        }
    }
    private class UpdateButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            ArrayList<String > info = adminView.getInfoForSelectClient();
            long id = adminView.getIdForSelectedAccount();
            String username = info.get(0);
            String password = info.get(1);
            User newEmpl = new UserBuilder().setId(id).setUsername(username).setPassword(password).build();


            userValidator.validate(newEmpl.getUsername(), newEmpl.getPassword());
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                employeeService.updateEmployee(newEmpl);
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Client updated!");
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), userValidator.getFormattedErrors());
            }
            adminView.resetTextFields();
        }
    }
    private class DeleteButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            long id = adminView.getIdForSelectedAccount();
            if(employeeService.deleteEmployee(id)){
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee deleted!");
            }else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "An error has occurred!");
            }
        }
    }

    private class ShowReportsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
            String date = adminView.getDate();
            String username = adminView.getUsernamerForSelectedAccount();
            FileWriter myWriter = new FileWriter("report.txt");
            myWriter.write(username + "\n");
            myWriter.write(date+"\n");
            ArrayList<ActionEmployee> actionEmployees = (ArrayList<ActionEmployee>)actionService.findActionForClient();
            boolean existsAction = false;
            for(ActionEmployee act : actionEmployees){
                    if (act.getUsername().equals(username) && act.getDate().equals(date)) {
                        myWriter.write( act.getName() +"\n");
                        existsAction = true;
                    }
                }
            if(!existsAction){
                myWriter.write("No actions");
            }
            myWriter.close();
            } catch (IOException a) {
                a.printStackTrace();
            }
        }
    }
}
