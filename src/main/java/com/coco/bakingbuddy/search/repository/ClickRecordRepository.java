package com.coco.bakingbuddy.search.repository;

import com.coco.bakingbuddy.search.domain.ClickRecord;
import com.coco.bakingbuddy.search.domain.ClickType;
import com.coco.bakingbuddy.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClickRecordRepository extends JpaRepository<ClickRecord, Long> {
    List<ClickRecord> findByUserAndClickType(User user, ClickType clickType);
}
