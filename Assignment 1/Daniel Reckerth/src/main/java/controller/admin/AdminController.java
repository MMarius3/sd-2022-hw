package controller.admin;

import controller.LoginController;
import controller.admin.listeners.DeleteEmployeeListener;
import controller.admin.listeners.EmployeeTableMouseListener;
import controller.admin.listeners.UpdateEmployeeListener;
import service.activity.EmployeeActivityService;
import service.user.UserService;
import view.admin.AdminView;

public class AdminController {
  private final AdminView view;
  private final UserService userService;
  private final EmployeeActivityService employeeActivityService;

  public AdminController(AdminView view, UserService userService, EmployeeActivityService employeeActivityService) {
    this.view = view;
    this.userService = userService;
    this.employeeActivityService = employeeActivityService;
    view.setDeleteButtonActionListener(new DeleteEmployeeListener(view, userService));
    view.setUpdateButtonActionListener(new UpdateEmployeeListener(view, userService));
    view.setEmployeeTableMouseListener(new EmployeeTableMouseListener(view, userService, employeeActivityService));
    view.fillEmployeeTable(userService.findAll());
  }

}
