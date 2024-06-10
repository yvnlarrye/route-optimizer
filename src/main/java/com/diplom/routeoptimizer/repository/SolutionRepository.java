package com.diplom.routeoptimizer.repository;

import com.diplom.routeoptimizer.model.Solution;
import com.diplom.routeoptimizer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolutionRepository extends JpaRepository<Solution, Long> {
    List<Solution> findAllByUser(User user);
}
