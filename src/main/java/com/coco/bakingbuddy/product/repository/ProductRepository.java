package com.coco.bakingbuddy.product.repository;

import com.coco.bakingbuddy.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProviderId(Long providerId);
}
