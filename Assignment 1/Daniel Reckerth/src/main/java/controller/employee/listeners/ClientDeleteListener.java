package controller.employee.listeners;

import controller.LoginController;
import model.EmployeeActivity;
import model.builder.EmployeeActivityBuilder;
import repository.security.RightsRolesRepository;
import service.account.AccountService;
import service.activity.EmployeeActivityService;
import service.client.ClientService;
import view.employee.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class ClientDeleteListener implements ActionListener {
  private final EmployeeView employeeView;
  private final ClientService clientService;
  private final AccountService accountService;
  private final EmployeeActivityService employeeActivityService;
  private final RightsRolesRepository rightsRolesRepository;
  private final LoginController loginController;

  public ClientDeleteListener(EmployeeView employeeView, ClientService clientService, AccountService accountService, EmployeeActivityService employeeActivityService, RightsRolesRepository rightsRolesRepository, LoginController loginController) {
    this.employeeView = employeeView;
    this.clientService = clientService;
    this.accountService = accountService;
    this.employeeActivityService = employeeActivityService;
    this.rightsRolesRepository = rightsRolesRepository;
    this.loginController = loginController;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(clientService.deleteById(Long.parseLong(employeeView.getClientId()))) {
      employeeView.showMessage("Client deleted successfully", JOptionPane.INFORMATION_MESSAGE);
      employeeView.fillClientTable(clientService.findAll());
      employeeView.fillAccountsTable(accountService.findAccountsByClientId(Long.parseLong(employeeView.getClientId())));
      EmployeeActivity employeeActivity = new EmployeeActivityBuilder()
              .setPerformedActivity(rightsRolesRepository.findRightByTitle("delete-client").get())
              .setEmployee(loginController.getLoggedInUser())
              .setPerformedAt(LocalDateTime.now())
              .build();
      employeeActivityService.save(employeeActivity);
    } else {
      employeeView.showMessage("Delete failed", JOptionPane.ERROR_MESSAGE);
    }

  }
}
