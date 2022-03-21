package controller;

import view.AdminView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController {
    private final AdminView adminView;
    private LoginController loginController;
    private final EmployeeController employeeController;
    private final EmployeeManagerController employeeManagerController;

    public AdminController(AdminView adminView, EmployeeController employeeController, EmployeeManagerController employeeManagerController) {
        this.adminView = adminView;
        this.employeeController = employeeController;
        this.employeeManagerController = employeeManagerController;

        this.adminView.addClientManagerButtonListener(new SelectClientManagerListener());
        this.adminView.addEmployeeManagerButtonListener(new SelectEmployeeManagerListener());
        this.adminView.addBackButtonListener(new BackButtonListener());
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    private class SelectClientManagerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            adminView.setVisible(false);
            employeeController.setLoginController(loginController);
            employeeController.getEmployeeView().setVisible(true);
        }
    }

    private class SelectEmployeeManagerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            adminView.setVisible(false);
            employeeManagerController.setLoginController(loginController);
            employeeManagerController.getEmployeeManagerView().setVisible(true);
        }
    }

    private class BackButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            adminView.setVisible(false);
            loginController.getLoginView().setVisible(true);
        }
    }
}
