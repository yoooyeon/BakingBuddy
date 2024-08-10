package com.coco.bakingbuddy.follow.repository;

import com.coco.bakingbuddy.follow.domain.Follow;
import com.coco.bakingbuddy.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollower(User user);

    List<Follow> findByFollowed(User user);
}
