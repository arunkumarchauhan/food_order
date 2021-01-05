package com.example.demo.service;

import com.example.demo.model.InvalidTokens;
import com.example.demo.repo.InvalidTokensRepo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class InvalidTokensService {
    @Autowired
    InvalidTokensRepo invalidTokensRepo;

    public List<InvalidTokens> getAllTokens() {
        try {
            log.debug("Fetching all Tokens");
            return invalidTokensRepo.findAll();
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return null;
        }
    }

    public InvalidTokens saveToken(String token) {
        try {
            log.debug("Saving token {}", token);
            InvalidTokens invalidTokens = new InvalidTokens();
            invalidTokens.setToken(token);
            return invalidTokensRepo.save(invalidTokens);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return null;
        }
    }

    public InvalidTokens getInvalidToken(String token) {
        try {
            log.debug("Getting token {}", token);
            return invalidTokensRepo.findByToken(token).get();
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return null;
        }
    }

    public Boolean isInvalid(String token) {
        try {
            log.debug("isInvalid token {}", token);
            return getInvalidToken(token) != null;
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return true;
        }
    }
}
