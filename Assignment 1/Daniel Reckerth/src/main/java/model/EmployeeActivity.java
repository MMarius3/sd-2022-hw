package model;

import java.time.LocalDateTime;

public class EmployeeActivity {

  private Long id;
  public LocalDateTime performedAt;
  public User employee;
  public Right performedActivity;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getPerformedAt() {
    return performedAt;
  }

  public void setPerformedAt(LocalDateTime performedAt) {
    this.performedAt = performedAt;
  }

  public User getEmployee() {
    return employee;
  }

  public void setEmployee(User employee) {
    this.employee = employee;
  }

  public Right getPerformedActivity() {
    return performedActivity;
  }

  public void setPerformedActivity(Right performedActivity) {
    this.performedActivity = performedActivity;
  }
}
