package controller;

import model.Action;
import model.User;
import model.validator.UserValidator;
import service.user.AdminService;
import view.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AdminController {

    private final AdminView adminView;
    private final AdminService adminService;
    private final UserValidator userValidator;

    public AdminController(AdminView adminView, AdminService adminService, UserValidator userValidator) {
        this.adminView = adminView;
        this.adminService = adminService;
        this.userValidator = userValidator;

        this.adminView.addNewEmployeeButtonListener(new AddNewEmployeeButtonListener());
        this.adminView.viewEmployeesButtonListener(new ViewEmployeesButtonListener());
        this.adminView.updateEmployeeButtonListener(new UpdateEmployeeButtonListener());
        this.adminView.deleteEmployeeButtonListener(new DeleteEmployeeButtonListener());
        this.adminView.generateReportButtonListener(new GenerateReportButtonListener());
    }

    private class AddNewEmployeeButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = adminView.getEmployeeName();
            String password = adminView.getEmployeePassword();
            userValidator.validate(name,password);
            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty()) {
                adminService.createEmployee(name, password);
            }else{
                JOptionPane.showMessageDialog(adminView.getContentPane(),userValidator.getFormattedErrors());
            }
        }
    }

    private class ViewEmployeesButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            List<User> users = adminService.findAll();
            adminView.setViewEmployeesTable(users);
        }
    }

    private class UpdateEmployeeButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = (Long) adminView.getEmployeeIdFromTable(adminView.getEmployeeTableSelection());
            String username = adminView.getEmployeeName();
            String password = adminView.getEmployeePassword();
            if(password.isEmpty() && !username.isEmpty()){
                userValidator.validateUsernameOnly(username);
            }else if(!password.isEmpty() && username.isEmpty()){
                userValidator.validatePasswordOnly(password);
            }else if(!password.isEmpty() && !username.isEmpty()) {
                userValidator.validate(username, password);
            }
            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty()) {
                adminService.updateEmployee(id, username, password);
            }else{
                JOptionPane.showMessageDialog(adminView.getContentPane(),userValidator.getFormattedErrors());
            }
        }
    }
    private class DeleteEmployeeButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = (Long) adminView.getEmployeeIdFromTable(adminView.getEmployeeTableSelection());
            adminService.deleteEmployee(id);

        }
    }

    private class GenerateReportButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = (Long) adminView.getEmployeeIdFromTable(adminView.getEmployeeTableSelection());
            int day1 = adminView.getDay1();
            int month1 = adminView.getMonth1();
            int year1 = adminView.getYear1();
            int day2 = adminView.getDay2();
            int month2 = adminView.getMonth2();
            int year2 = adminView.getYear2();
            Date date1 = new GregorianCalendar(year1,month1-1,day1).getTime();
            Date date2 = new GregorianCalendar(year2,month2-1,day2).getTime();
            List<Action> actions = adminService.generateReport(id,date1,date2);
            adminView.setViewActionsTable(actions);
        }
    }

}
