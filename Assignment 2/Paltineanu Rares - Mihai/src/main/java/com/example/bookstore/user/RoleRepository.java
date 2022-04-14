package com.example.bookstore.user;

import com.example.bookstore.user.model.ERole;
import com.example.bookstore.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
