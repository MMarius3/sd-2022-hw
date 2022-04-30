package controller.admin.listeners;

import model.User;
import model.builder.UserBuilder;
import model.validation.UserValidator;
import service.user.UserService;
import view.admin.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateEmployeeListener implements ActionListener {
  private final AdminView view;
  private final UserService employeeService;

  public UpdateEmployeeListener(AdminView view, UserService employeeService) {
    this.view = view;
    this.employeeService = employeeService;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Long id = Long.parseLong(view.getEmployeeId());
    String email = view.getEmail();
    String password = view.getPassword();
    User employeeToUpdate = new UserBuilder()
            .setUsername(email)
            .setPassword(password)
            .build();
    UserValidator userValidator = new UserValidator(employeeToUpdate);
    if(userValidator.validate()) {
      if(employeeService.update(id, employeeToUpdate)){
        view.showMessage("Employee updated successfully!", JOptionPane.INFORMATION_MESSAGE);
        view.fillEmployeeTable(employeeService.findAll());
      } else {
        view.showMessage("Employee update failed!", JOptionPane.ERROR_MESSAGE);
      }
    } else {
      view.showMessage(userValidator.getFormattedErrors(), JOptionPane.ERROR_MESSAGE);
    }
  }
}
