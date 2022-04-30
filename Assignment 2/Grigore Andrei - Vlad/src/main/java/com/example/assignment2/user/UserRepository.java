package com.example.assignment2.user;

import com.example.assignment2.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE User i SET i.username = ?2 WHERE i.id = ?1")
    Optional<User> updateUsername(@Param("id")Long id, @Param("title") String username);

    @Modifying
    @Transactional
    @Query (nativeQuery = true, value = "UPDATE User i SET i.email = ?2 WHERE i.id = ?1")
    Optional<User> updateEmail(Long id, String email);

    @Modifying
    @Transactional
    @Query (nativeQuery = true, value = "UPDATE User i SET i.password = ?2 WHERE i.id = ?1")
    Optional<User> updatePassword(Long id, String password);


}
