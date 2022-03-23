package service.activity;

import model.EmployeeActivity;
import repository.activity.EmployeeActivityRepository;

import java.util.List;

public class EmployeeActivityServiceImpl implements EmployeeActivityService {

  private final EmployeeActivityRepository employeeActivityRepository;

  public EmployeeActivityServiceImpl(EmployeeActivityRepository employeeActivityRepository) {
    this.employeeActivityRepository = employeeActivityRepository;
  }

  @Override
  public List<EmployeeActivity> findAll() {
    return employeeActivityRepository.findAll();
  }

  @Override
  public EmployeeActivity findById(Long id) {
    return employeeActivityRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No such id."));
  }

  @Override
  public boolean save(EmployeeActivity employeeActivity) {
    return employeeActivityRepository.save(employeeActivity);
  }

  @Override
  public boolean update(Long id, EmployeeActivity employeeActivity) {
    return employeeActivityRepository.update(id, employeeActivity);
  }

  @Override
  public boolean deleteById(Long id) {
    return employeeActivityRepository.deleteById(id);
  }

  @Override
  public void deleteAll() {
    employeeActivityRepository.deleteAll();
  }
}
