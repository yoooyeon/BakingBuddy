package com.coco.bakingbuddy.search.repository;

import com.coco.bakingbuddy.search.domain.ClickRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClickRecordRepository extends JpaRepository<ClickRecord, Long> {
}
