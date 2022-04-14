package Repository.User;

import Controller.Response;
import Model.User;
import javafx.collections.ObservableList;


public interface UserRepository {

    ObservableList<User> findAll();

    User findById(Long id);

    User findByUsernameAndPassword(String username, String password);

    boolean save(User user);
    boolean update (Long id , String username,String password);
    boolean delete(String username);

    void removeAll();

    Response<Boolean> existsByUsername(String email);
}
