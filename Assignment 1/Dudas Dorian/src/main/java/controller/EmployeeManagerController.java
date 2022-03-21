package controller;

import model.User;
import model.builder.UserBuilder;
import model.validator.DateValidator;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import service.employeeActivity.EmployeeActivityService;
import service.user.AuthenticationService;
import service.user.UserInfoService;
import view.EmployeeManagerView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class EmployeeManagerController {
    private final EmployeeManagerView employeeManagerView;
    private final UserInfoService userInfoService;
    private final EmployeeActivityService employeeActivityService;
    private final RightsRolesRepository rightsRolesRepository;
    private final AuthenticationService authenticationService;
    private LoginController loginController;

    private final UserValidator userValidator;
    private final DateValidator dateValidator;

    public EmployeeManagerController(EmployeeManagerView employeeManagerView, UserInfoService userInfoService, EmployeeActivityService employeeActivityService, RightsRolesRepository rightsRolesRepository, AuthenticationService authenticationService, UserValidator userValidator, DateValidator dateValidator) {
        this.employeeManagerView = employeeManagerView;
        this.userInfoService = userInfoService;
        this.employeeActivityService = employeeActivityService;
        this.rightsRolesRepository = rightsRolesRepository;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.dateValidator = dateValidator;

        this.employeeManagerView.addViewEmployeesButtonListener(new ShowEmployeesListener());
        this.employeeManagerView.addCreateEmployeesButtonListener(new CreateEmployeeListener());
        this.employeeManagerView.addUpdateEmployeesButtonListener(new UpdateEmployeeListener());
        this.employeeManagerView.addDeleteEmployeesButtonListener(new DeleteEmployeeListener());
        this.employeeManagerView.addGenerateReportListener(new GenerateReportListener());

        this.employeeManagerView.addBackButtonListener(new BackButtonListener());
    }

    public EmployeeManagerView getEmployeeManagerView() {
        return employeeManagerView;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    private void refreshTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Username");
        model.addColumn("Password");

        List<User> employees = userInfoService.findAllWithRole(rightsRolesRepository.findRoleByTitle(EMPLOYEE));
        for(User u : employees){
            Object[] row = {u.getId(), u.getUsername(), u.getPassword()};
            model.insertRow(model.getRowCount(), row);
        }
        employeeManagerView.getEmployeeTable().setModel(model);
    }

    private class ShowEmployeesListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshTable();
        }
    }

    private class CreateEmployeeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            userValidator.validate(getUserFromTextFields().getUsername(), getUserFromTextFields().getPassword());
            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty()){
                authenticationService.register(getUserFromTextFields().getUsername(), getUserFromTextFields().getPassword());
                refreshTable();
            }
            else {
                JOptionPane.showMessageDialog(employeeManagerView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }

    private class UpdateEmployeeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = employeeManagerView.getEmployeeTable().getSelectedRow();
            Long id = (Long) employeeManagerView.getEmployeeTable().getValueAt(row, 0);
            userValidator.validate(getUserFromTextFields().getUsername(), getUserFromTextFields().getPassword());
            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty()) {
                userInfoService.updateById(id, getUserFromTextFields());
                refreshTable();
            }
            else {
                JOptionPane.showMessageDialog(employeeManagerView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }

    private class DeleteEmployeeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = employeeManagerView.getEmployeeTable().getSelectedRow();
            Long id = (Long) employeeManagerView.getEmployeeTable().getValueAt(row, 0);
            userInfoService.removeById(id);
            refreshTable();
        }
    }

    private class BackButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeManagerView.setVisible(false);
            loginController.getLoginView().setVisible(true);
        }
    }

    private class GenerateReportListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String sDateString = employeeManagerView.getTfStartDate().getText();
                String eDateString = employeeManagerView.getTfEndDate().getText();

                dateValidator.validate(sDateString);
                dateValidator.validate(eDateString);
                final List<String> errors = userValidator.getErrors();
                if(errors.isEmpty()){
                    Date sDate = new SimpleDateFormat("dd/MM/yyyy").parse(sDateString);
                    Date eDate = new SimpleDateFormat("dd/MM/yyyy").parse(eDateString);

                    int row = employeeManagerView.getEmployeeTable().getSelectedRow();
                    Long id = (Long) employeeManagerView.getEmployeeTable().getValueAt(row, 0);

                    List<String> reports = employeeActivityService.generateReport(sDate, eDate, id);
                    for(String s : reports){
                        System.out.print(s);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(employeeManagerView.getContentPane(), dateValidator.getFormattedErrors());
                }

            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }

    private User getUserFromTextFields(){
        User user = new UserBuilder()
                .setUsername(employeeManagerView.getTfUsername().getText())
                .setPassword(employeeManagerView.getTfPassword().getText())
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE)))
                .build();
        return user;
    }
}
