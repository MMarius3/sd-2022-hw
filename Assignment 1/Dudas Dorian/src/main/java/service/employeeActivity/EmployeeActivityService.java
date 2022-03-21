package service.employeeActivity;

import model.EmployeeActivity;

import java.util.List;

public interface EmployeeActivityService {
    List<EmployeeActivity> findAll();

    boolean save(EmployeeActivity employeeActivity);
}
