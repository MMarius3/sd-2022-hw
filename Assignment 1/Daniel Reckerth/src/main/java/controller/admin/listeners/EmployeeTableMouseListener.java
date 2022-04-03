package controller.admin.listeners;

import service.activity.EmployeeActivityService;
import service.user.UserService;
import view.admin.AdminView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EmployeeTableMouseListener implements MouseListener {

  private final AdminView adminView;
  private final UserService userService;
  private final EmployeeActivityService employeeActivityService;

  public EmployeeTableMouseListener(AdminView adminView, UserService userService, EmployeeActivityService employeeActivityService) {
    this.adminView = adminView;
    this.userService = userService;
    this.employeeActivityService = employeeActivityService;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int row = adminView.getEmployeesTable().getSelectedRow();
    if(row != -1) {
      adminView.setEmployeeInfoOfRow(row);
      adminView.fillActivitiesTable(employeeActivityService.findActivitiesByEmployeeId(Long.parseLong(adminView.getEmployeeId())));
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
