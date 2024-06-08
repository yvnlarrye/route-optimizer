package com.diplom.routeoptimizer.repository;

import com.diplom.routeoptimizer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String email);
    Boolean existsByUsername(String email);
}

