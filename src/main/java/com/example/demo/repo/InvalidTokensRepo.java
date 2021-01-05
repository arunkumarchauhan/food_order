package com.example.demo.repo;

import com.example.demo.model.InvalidTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvalidTokensRepo extends JpaRepository<InvalidTokens, Long> {
    Optional<InvalidTokens> findByToken(String token);
}
