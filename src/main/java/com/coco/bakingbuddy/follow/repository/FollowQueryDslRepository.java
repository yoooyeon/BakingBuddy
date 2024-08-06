package com.coco.bakingbuddy.follow.repository;

import com.coco.bakingbuddy.follow.domain.Follow;
import com.coco.bakingbuddy.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.follow.domain.QFollow.follow;

@RequiredArgsConstructor
@Repository
public class FollowQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public long unFollow(User follower, User followed) {
        return queryFactory
                .delete(follow)
                .where(follow.follower.id.eq(follower.getId())
                        .and(follow.followed.id.eq(followed.getId()))
                )
                .execute(); // execute() 메소드로 삭제된 행의 수를 반환
    }

    public boolean existsByFollowerAndFollowed(User follower, User followed) {
        return queryFactory
                .selectFrom(follow)
                .where(follow.follower.eq(follower)
                        .and(follow.followed.eq(followed)))
                .fetchCount() > 0;
    }
}
