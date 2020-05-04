package com.gavinkim.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    long countByUsername(String username);
    Optional<User> findByUsername(String username);
    long findByEmail(String email);
}
