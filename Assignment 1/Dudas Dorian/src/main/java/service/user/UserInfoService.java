package service.user;

import model.Role;
import model.User;

import java.util.List;

public interface UserInfoService {
    List<User> findAllWithRole(Role role);

    User findById(Long id);

    boolean save(User user);

    boolean updateById(Long id, User user);

    boolean removeById(Long id);
}
