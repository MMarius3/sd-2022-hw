package service.employeeActivity;

import model.EmployeeActivity;

import java.util.List;

public interface EmployeeActivityService {
    List<EmployeeActivity> findAll();

    boolean save(EmployeeActivity employeeActivity);

    List<String> generateReport(java.util.Date startDate, java.util.Date endDate, Long id);
}
