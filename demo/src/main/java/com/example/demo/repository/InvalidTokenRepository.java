package com.example.demo.repository;

import com.example.demo.model.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Integer> {
    @Query(value = "SELECT u from  InvalidToken u WHERE u.token = ?1")
    public InvalidToken getInvalidTokenByToken(String token);
}
