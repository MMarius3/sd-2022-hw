package controller.employee.listeners;

import controller.LoginController;
import model.Account;
import model.Client;
import model.EmployeeActivity;
import model.builder.AccountBuilder;
import model.builder.EmployeeActivityBuilder;
import model.enums.AccountType;
import model.validation.AccountValidator;
import repository.security.RightsRolesRepository;
import service.account.AccountService;
import service.activity.EmployeeActivityService;
import service.client.ClientService;
import view.employee.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SaveAccountListener implements ActionListener {
  private final EmployeeView employeeView;
  private final AccountService accountService;
  private final ClientService clientService;
  private final EmployeeActivityService employeeActivityService;
  private final RightsRolesRepository rightsRolesRepository;
  private final LoginController loginController;

  public SaveAccountListener(EmployeeView employeeView, AccountService accountService, ClientService clientService, EmployeeActivityService employeeActivityService, RightsRolesRepository rightsRolesRepository, LoginController loginController) {
    this.employeeView = employeeView;
    this.accountService = accountService;
    this.clientService = clientService;
    this.employeeActivityService = employeeActivityService;
    this.rightsRolesRepository = rightsRolesRepository;
    this.loginController = loginController;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    final String accountType = employeeView.getAccType();
    final double balance = Double.parseDouble(employeeView.getBalance());
    final LocalDate creationDate = LocalDate.parse(employeeView.getCreationDate());
    Account account = new AccountBuilder()
            .setAccountType(AccountType.valueOfLabel(accountType))
            .setBalance(balance)
            .setCreationDate(creationDate)
            .build();
    Client client = clientService.findById(Long.parseLong(employeeView.getClientId()));
    account.setClient(client);
    AccountValidator accountValidator = new AccountValidator(account);
    if(accountValidator.validate()){
      if(accountService.save(account)){
        employeeView.showMessage("Account saved successfully!", JOptionPane.INFORMATION_MESSAGE);
        EmployeeActivity employeeActivity = new EmployeeActivityBuilder()
                .setPerformedActivity(rightsRolesRepository.findRightByTitle("create-account").get())
                .setEmployee(loginController.getLoggedInUser())
                .setPerformedAt(LocalDateTime.now())
                .build();
        employeeActivityService.save(employeeActivity);
      } else {
        employeeView.showMessage("Save failed!", JOptionPane.ERROR_MESSAGE);
      }
    } else {
      employeeView.showMessage(accountValidator.getFormattedErrors(), JOptionPane.ERROR_MESSAGE);
      employeeView.clearAccountFields();
    }
  }
}
