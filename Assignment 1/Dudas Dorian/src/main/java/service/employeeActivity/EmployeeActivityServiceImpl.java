package service.employeeActivity;

import model.EmployeeActivity;
import repository.employeeActivity.EmployeeActivityRepository;

import java.util.Date;
import java.util.List;

public class EmployeeActivityServiceImpl implements EmployeeActivityService{
    private final EmployeeActivityRepository employeeActivityRepository;

    public EmployeeActivityServiceImpl(EmployeeActivityRepository employeeActivityRepository) {
        this.employeeActivityRepository = employeeActivityRepository;
    }

    @Override
    public List<EmployeeActivity> findAll() {
        return employeeActivityRepository.findAll();
    }

    @Override
    public boolean save(EmployeeActivity employeeActivity) {
        return employeeActivityRepository.save(employeeActivity);
    }

    @Override
    public List<String> generateReport(Date startDate, Date endDate, Long id) {
        return employeeActivityRepository.generateReport(startDate, endDate, id);
    }
}
