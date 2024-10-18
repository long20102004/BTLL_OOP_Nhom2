package com.example.demo.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.InvalidateToken;
import com.example.demo.repository.InvalidateRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class InvalidateService {
    InvalidateRepository invalidateRepository;

    public void checkExpriedToken() {
        List<InvalidateToken> tokens = invalidateRepository.findAll();
        tokens.forEach(token -> {
            if (token.getExpireTime().before(new Date(Instant.now().toEpochMilli()))) {
                invalidateRepository.delete(token);
            }
        });
    }
}
