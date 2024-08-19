package com.coco.bakingbuddy.admin.repository;

import com.coco.bakingbuddy.admin.domain.Report;
import com.coco.bakingbuddy.admin.domain.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
}
