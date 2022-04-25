package com.lab4.demo.user;

import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(nativeQuery = true, value = "select u.email, u.username, u.password from user u, role r, user_roles ur" +
            " where u.id = ur.user_id and r.id = ur.role_id and r.name = ?1")
    List<User> findAllByRoleEquals(String role);

    List<User> findUserByRolesIsContaining(Role role);
}
