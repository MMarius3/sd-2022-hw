package Service.User;

import Model.User;

public interface AuthenticationService {

    boolean register(String username, String password);

    User login(String username, String password);

    boolean logout(User user);
}
