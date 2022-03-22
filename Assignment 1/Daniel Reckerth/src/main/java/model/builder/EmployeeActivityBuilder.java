package model.builder;

import model.EmployeeActivity;
import model.Right;
import model.User;

import java.time.LocalDateTime;

public class EmployeeActivityBuilder {

  private final EmployeeActivity employeeActivity;


  public EmployeeActivityBuilder() {
    employeeActivity = new EmployeeActivity();
  }

  public EmployeeActivityBuilder setId(Long id) {
    employeeActivity.setId(id);
    return this;
  }

  public EmployeeActivityBuilder setPerformedAt(LocalDateTime performedAt) {
    employeeActivity.setPerformedAt(performedAt);
    return this;
  }

  public EmployeeActivityBuilder setEmployee(User employee) {
    employeeActivity.setEmployee(employee);
    return this;
  }

  public EmployeeActivityBuilder setPerformedActivity(Right activity) {
    employeeActivity.setPerformedActivity(activity);
    return this;
  }

  public EmployeeActivity build() {
    return employeeActivity;
  }
}
