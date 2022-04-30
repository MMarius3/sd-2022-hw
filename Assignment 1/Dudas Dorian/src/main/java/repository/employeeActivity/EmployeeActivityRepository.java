package repository.employeeActivity;

import model.EmployeeActivity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeActivityRepository {
    List<EmployeeActivity> findAll();

    Optional<EmployeeActivity> findById(Long id);

    boolean save(EmployeeActivity employeeActivity);

    void removeAll();

    List<String> generateReport(Date startDate, Date endDate, Long id);
}
