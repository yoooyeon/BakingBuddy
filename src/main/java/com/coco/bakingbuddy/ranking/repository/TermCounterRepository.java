package com.coco.bakingbuddy.ranking.repository;

import com.coco.bakingbuddy.ranking.domain.TermCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermCounterRepository extends JpaRepository<TermCounter, Long> {
    Optional<TermCounter> findByTerm(String searchTerm);
}
