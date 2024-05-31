package com.diplom.routeoptimizer.services;

import com.diplom.routeoptimizer.model.User;
import com.diplom.routeoptimizer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User create(User user) {
        return repository.save(user);
    }

    public Boolean isUserExists(String email) {
        return repository.existsByUsername(email);
    }

    public User getByEmail(String email) {
        return repository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    public User getCurrentUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(email);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

}
