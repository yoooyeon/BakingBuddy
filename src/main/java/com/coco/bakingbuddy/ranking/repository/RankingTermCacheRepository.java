package com.coco.bakingbuddy.ranking.repository;

import com.coco.bakingbuddy.ranking.domain.RankingTermCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingTermCacheRepository extends JpaRepository<RankingTermCache, Long> {

}
