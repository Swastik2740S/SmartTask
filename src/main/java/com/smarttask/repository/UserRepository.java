package com.smarttask.repository;

import com.smarttask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);           // <-- Add this line
    Optional<User> findByEmail(String email);      // (optional, for fetching user by email)
}
