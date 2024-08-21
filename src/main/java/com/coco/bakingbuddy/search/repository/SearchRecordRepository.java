package com.coco.bakingbuddy.search.repository;

import com.coco.bakingbuddy.search.domain.SearchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRecordRepository extends JpaRepository<SearchRecord, Long> {
}
