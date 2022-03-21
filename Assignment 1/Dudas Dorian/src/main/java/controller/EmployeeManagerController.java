package controller;

import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import service.user.AuthenticationService;
import service.user.UserInfoService;
import view.EmployeeManagerView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class EmployeeManagerController {
    private final EmployeeManagerView employeeManagerView;
    private final UserInfoService userInfoService;
    private final RightsRolesRepository rightsRolesRepository;
    private final AuthenticationService authenticationService;
    private LoginController loginController;

    public EmployeeManagerController(EmployeeManagerView employeeManagerView, UserInfoService userInfoService, RightsRolesRepository rightsRolesRepository, AuthenticationService authenticationService) {
        this.employeeManagerView = employeeManagerView;
        this.userInfoService = userInfoService;
        this.rightsRolesRepository = rightsRolesRepository;
        this.authenticationService = authenticationService;

        this.employeeManagerView.addViewEmployeesButtonListener(new ShowEmployeesListener());
        this.employeeManagerView.addCreateEmployeesButtonListener(new CreateEmployeeListener());
        this.employeeManagerView.addUpdateEmployeesButtonListener(new UpdateEmployeeListener());
        this.employeeManagerView.addDeleteEmployeesButtonListener(new DeleteEmployeeListener());

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
            authenticationService.register(getUserFromTextFields().getUsername(), getUserFromTextFields().getPassword());
            refreshTable();
        }
    }

    private class UpdateEmployeeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = employeeManagerView.getEmployeeTable().getSelectedRow();
            Long id = (Long) employeeManagerView.getEmployeeTable().getValueAt(row, 0);
            userInfoService.updateById(id, getUserFromTextFields());
            refreshTable();
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

    private User getUserFromTextFields(){
        User user = new UserBuilder()
                .setUsername(employeeManagerView.getTfUsername().getText())
                .setPassword(employeeManagerView.getTfPassword().getText())
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE)))
                .build();
        return user;
    }
}
