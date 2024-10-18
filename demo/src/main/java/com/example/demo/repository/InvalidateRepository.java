package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.InvalidateToken;

@Repository
public interface InvalidateRepository extends JpaRepository<InvalidateToken, String> {
    boolean existsByToken(String token);
}
