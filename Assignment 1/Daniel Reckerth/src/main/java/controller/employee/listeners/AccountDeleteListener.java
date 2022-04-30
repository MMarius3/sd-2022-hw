package controller.employee.listeners;

import controller.LoginController;
import model.EmployeeActivity;
import model.builder.EmployeeActivityBuilder;
import repository.security.RightsRolesRepository;
import service.account.AccountService;
import service.activity.EmployeeActivityService;
import view.employee.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class AccountDeleteListener implements ActionListener {
  private final EmployeeView employeeView;
  private final AccountService accountService;
  private final EmployeeActivityService employeeActivityService;
  private final RightsRolesRepository rightsRolesRepository;
  private final LoginController loginController;

  public AccountDeleteListener(EmployeeView employeeView, AccountService accountService, EmployeeActivityService employeeActivityService, RightsRolesRepository rightsRolesRepository, LoginController loginController) {
    this.employeeView = employeeView;
    this.accountService = accountService;
    this.employeeActivityService = employeeActivityService;
    this.rightsRolesRepository = rightsRolesRepository;
    this.loginController = loginController;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(accountService.deleteById(Long.parseLong(employeeView.getAccountId()))) {
      employeeView.showMessage("Account deleted successfully", JOptionPane.INFORMATION_MESSAGE);
      employeeView.fillAccountsTable(accountService.findAccountsByClientId(Long.parseLong(employeeView.getClientId())));
      EmployeeActivity employeeActivity = new EmployeeActivityBuilder()
              .setPerformedActivity(rightsRolesRepository.findRightByTitle("delete-account").get())
              .setEmployee(loginController.getLoggedInUser())
              .setPerformedAt(LocalDateTime.now())
              .build();
      employeeActivityService.save(employeeActivity);
    } else {
      employeeView.showMessage("Account not found", JOptionPane.ERROR_MESSAGE);
    }
  }
}
