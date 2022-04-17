package controller.admin.listeners;

import service.user.UserService;
import view.admin.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteEmployeeListener implements ActionListener {
  private final AdminView view;
  private final UserService employeeService;

  public DeleteEmployeeListener(AdminView view, UserService employeeService) {
    this.view = view;
    this.employeeService = employeeService;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(employeeService.deleteById(Long.parseLong(view.getEmployeeId()))) {
      view.showMessage("Employee deleted successfully", JOptionPane.INFORMATION_MESSAGE);
      view.fillEmployeeTable(employeeService.findAll());
    } else {
      view.showMessage("Employee not deleted", JOptionPane.ERROR_MESSAGE);
    }
  }
}
