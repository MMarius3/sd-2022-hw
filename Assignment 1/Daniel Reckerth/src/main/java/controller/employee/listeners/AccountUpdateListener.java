package controller.employee.listeners;

import controller.LoginController;
import model.Account;
import model.EmployeeActivity;
import model.builder.AccountBuilder;
import model.builder.EmployeeActivityBuilder;
import model.enums.AccountType;
import model.validation.AccountValidator;
import repository.security.RightsRolesRepository;
import service.account.AccountService;
import service.activity.EmployeeActivityService;
import view.employee.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AccountUpdateListener implements ActionListener {
  private final EmployeeView employeeView;
  private final AccountService accountService;
  private final EmployeeActivityService employeeActivityService;
  private final RightsRolesRepository rightsRolesRepository;
  private final LoginController loginController;

  public AccountUpdateListener(EmployeeView employeeView, AccountService accountService, EmployeeActivityService employeeActivityService, RightsRolesRepository rightsRolesRepository, LoginController loginController) {
    this.employeeView = employeeView;
    this.accountService = accountService;
    this.employeeActivityService = employeeActivityService;
    this.rightsRolesRepository = rightsRolesRepository;
    this.loginController = loginController;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    final Long accountId = Long.parseLong(employeeView.getAccountId());
    final String accountType = employeeView.getAccType();
    final double balance = Double.parseDouble(employeeView.getBalance());
    final LocalDate creationDate = LocalDate.parse(employeeView.getCreationDate());
    Account account = new AccountBuilder()
            .setAccountType(AccountType.valueOfLabel(accountType))
            .setBalance(balance)
            .setCreationDate(creationDate)
            .build();

    AccountValidator accountValidator = new AccountValidator(account);
    if(accountValidator.validate()){
      if(accountService.update(accountId, account)){
        employeeView.showMessage("Account updated successfully!", JOptionPane.INFORMATION_MESSAGE);
        EmployeeActivity employeeActivity = new EmployeeActivityBuilder()
                .setPerformedActivity(rightsRolesRepository.findRightByTitle("update-account").get())
                .setEmployee(loginController.getLoggedInUser())
                .setPerformedAt(LocalDateTime.now())
                .build();
        employeeActivityService.save(employeeActivity);
      } else {
        employeeView.showMessage("Update failed!", JOptionPane.ERROR_MESSAGE);
        employeeView.clearAccountFields();
      }
    } else {
      employeeView.showMessage(accountValidator.getFormattedErrors(), JOptionPane.ERROR_MESSAGE);
      employeeView.clearAccountFields();
    }
  }
}
