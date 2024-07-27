package com.coco.bakingbuddy.search.repository;

import com.coco.bakingbuddy.search.domain.RecentSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {
}
