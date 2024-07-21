package com.coco.bakingbuddy.jwt.repository;

import com.coco.bakingbuddy.jwt.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    RefreshToken findByUsername(String username);
}