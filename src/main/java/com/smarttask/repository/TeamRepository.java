package com.smarttask.repository;

import com.smarttask.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
    Page<Team> findAll(Pageable pageable);
}
