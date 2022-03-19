package Service.Employee;

import Model.User;
import javafx.collections.ObservableList;


public interface EmployeeService {

    boolean updateEmp(Long id,String username,String password);

    boolean deleteEmp(String username);

    ObservableList<User> viewEmp();

}
