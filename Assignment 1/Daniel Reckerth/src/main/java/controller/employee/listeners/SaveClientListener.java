package controller.employee.listeners;

import controller.LoginController;
import model.Client;
import model.EmployeeActivity;
import model.builder.ClientBuilder;
import model.builder.EmployeeActivityBuilder;
import model.validation.ClientValidator;
import repository.security.RightsRolesRepository;
import service.activity.EmployeeActivityService;
import service.client.ClientService;
import view.employee.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class SaveClientListener implements ActionListener {
  private final EmployeeView employeeView;
  private final ClientService clientService;
  private final EmployeeActivityService employeeActivityService;
  private final RightsRolesRepository rightsRolesRepository;
  private final LoginController loginController;

  public SaveClientListener(LoginController loginController, EmployeeView employeeView, ClientService clientService, EmployeeActivityService employeeActivityService, RightsRolesRepository rightsRolesRepository) {
    this.loginController = loginController;
    this.employeeView = employeeView;
    this.clientService = clientService;
    this.employeeActivityService = employeeActivityService;
    this.rightsRolesRepository = rightsRolesRepository;
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    final String clientName = employeeView.getName();
    final String clientAddress = employeeView.getAddress();
    final String clientSSN = employeeView.getCNP();
    final String clientCardNumber = employeeView.getCardNumber();
    Client client = new ClientBuilder()
            .setName(clientName)
            .setAddress(clientAddress)
            .setSSN(clientSSN)
            .setCardNumber(clientCardNumber)
            .build();

    ClientValidator clientValidator = new ClientValidator(client);
    if(clientValidator.validate()) {
      if(clientService.save(client)) {
        employeeView.showMessage("Client saved successfully!", JOptionPane.INFORMATION_MESSAGE);
        employeeView.fillClientTable(clientService.findAll());
        EmployeeActivity employeeActivity = new EmployeeActivityBuilder()
                .setPerformedActivity(rightsRolesRepository.findRightByTitle("create-client").get())
                .setEmployee(loginController.getLoggedInUser())
                .setPerformedAt(LocalDateTime.now())
                .build();
        employeeActivityService.save(employeeActivity);
      } else {
        employeeView.showMessage("Save failed", JOptionPane.ERROR_MESSAGE);
      }
      employeeView.clearClientFields();
    } else {
      employeeView.showMessage(clientValidator.getFormattedErrors(), JOptionPane.ERROR_MESSAGE);
      employeeView.clearClientFields();
    }
  }
}
