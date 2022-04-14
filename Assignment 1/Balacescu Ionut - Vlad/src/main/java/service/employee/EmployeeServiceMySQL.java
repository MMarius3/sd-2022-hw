package service.employee;

import model.User;
import repository.employee.EmployeeRepository;

import java.util.List;

public class EmployeeServiceMySQL implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceMySQL(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<User> findAllEmployees() {
       return employeeRepository.findAllEmployees();
    }

    @Override
    public boolean deleteEmployee(long id) {
        return employeeRepository.deleteEmployee(id);
    }

    @Override
    public boolean updateEmployee(User employee) {
       return employeeRepository.updateEmployee(employee);
    }

    @Override
    public User findById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public boolean addEmployee(User employee) {
       return employeeRepository.addEmployee(employee);
    }
}
