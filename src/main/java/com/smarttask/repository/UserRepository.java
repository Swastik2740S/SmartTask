package com.smarttask.repository;

import com.smarttask.enums.Role;
import com.smarttask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role); // Add this line to support role-based queries
}
