package com.diplom.routeoptimizer.repository;

import com.diplom.routeoptimizer.model.Token;
import com.diplom.routeoptimizer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<List<Token>> findAllByUser(User user);
    Optional<Token> findByToken(String token);
}
