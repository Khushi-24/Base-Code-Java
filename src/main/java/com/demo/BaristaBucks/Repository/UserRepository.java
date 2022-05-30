package com.demo.BaristaBucks.Repository;

import com.demo.BaristaBucks.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE email = ?1", nativeQuery = true)
    User findByUserEmail(String email);

    User findByEmailAndPassword(String userName, String encode);
}
