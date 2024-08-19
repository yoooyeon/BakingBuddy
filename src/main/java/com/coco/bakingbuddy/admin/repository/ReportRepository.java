package com.coco.bakingbuddy.admin.repository;

import com.coco.bakingbuddy.admin.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
