package repository.activity;

import model.EmployeeActivity;
import repository.Repository;

import java.util.List;

public interface EmployeeActivityRepository extends Repository<Long, EmployeeActivity> {
  List<EmployeeActivity> findActivitiesByEmployeeId(Long id);
}
