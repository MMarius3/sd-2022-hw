package service.activity;

import model.EmployeeActivity;
import service.Service;

import java.util.List;

public interface EmployeeActivityService extends Service<Long, EmployeeActivity> {
  List<EmployeeActivity> findActivitiesByEmployeeId(Long employeeId);
}
