package service.user;

import model.User;

public interface AuthenticationService {

    boolean register(String username, String password);

    User login(String username, String password);

    boolean logout(User user);

    boolean isEmployee(String username, String password);

    User findById(Long id);

    boolean save(User user);

    boolean update(Long id, String name);

    boolean delete(Long id);

    boolean addEmployee(String name);

    boolean updateEmployee(Long id, String name);

    User viewEmployee(Long id);

    boolean deleteEmployee(Long id);

    boolean generateReport (Long id);

}