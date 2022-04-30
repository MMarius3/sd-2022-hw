package controller.employee;

import controller.LoginController;
import controller.employee.listeners.*;
import repository.security.RightsRolesRepository;
import service.account.AccountService;
import service.activity.EmployeeActivityService;
import service.client.ClientService;
import view.employee.EmployeeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EmployeeController {
  private final EmployeeView employeeView;
  private final ClientService clientService;
  private final AccountService accountService;
  private final EmployeeActivityService employeeActivityService;
  private final RightsRolesRepository rightsRolesRepository;
  private final LoginController loginController;


  public EmployeeController(EmployeeView employeeView, ClientService clientService, AccountService accountService, EmployeeActivityService employeeActivityService, RightsRolesRepository rightsRolesRepository, LoginController loginController) {
    this.employeeView = employeeView;
    this.clientService = clientService;
    this.accountService = accountService;
    this.employeeActivityService = employeeActivityService;
    this.rightsRolesRepository = rightsRolesRepository;
    this.loginController = loginController;
    employeeView.setClientSaveButtonListener(new SaveClientListener(this.loginController, employeeView,clientService, this.employeeActivityService, this.rightsRolesRepository));
    employeeView.setAccountSaveButtonListener(new SaveAccountListener(employeeView,accountService, clientService, this.employeeActivityService, this.rightsRolesRepository, this.loginController));
    employeeView.setClientRowMouseListener(new ClientTableMouseListener(employeeView, accountService));
    employeeView.setAccountRowMouseListener(new AccountTableMouseListener(employeeView));
    employeeView.setClearClientButtonListener(new ClearClientButtonListener());
    employeeView.setClearAccountButtonListener(new ClearAccountButtonListener());
    employeeView.setDeleteClientButtonListener(new ClientDeleteListener(employeeView, clientService, accountService, this.employeeActivityService, this.rightsRolesRepository, this.loginController));
    employeeView.setDeleteAccountButtonListener(new AccountDeleteListener(employeeView, accountService, this.employeeActivityService, this.rightsRolesRepository, this.loginController));
    employeeView.setUpdateAccountButtonListener(new AccountUpdateListener(employeeView, accountService, this.employeeActivityService, this.rightsRolesRepository, this.loginController));
    employeeView.setUpdateClientButtonListener(new ClientUpdateListener(employeeView, clientService, this.employeeActivityService, this.rightsRolesRepository, this.loginController));
    employeeView.fillClientTable(clientService.findAll());
  }

  private class ClearClientButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      employeeView.clearClientFields();
    }
  }

  private class ClearAccountButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      employeeView.clearAccountFields();
    }
  }


  public EmployeeView getEmployeeView() {
    return employeeView;
  }
}
