package com.coco.bakingbuddy.admin.repository;

import com.coco.bakingbuddy.admin.domain.UserAuthority;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.admin.domain.QUserAuthority.userAuthority;

@RequiredArgsConstructor
@Repository
public class UserAuthorityQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<UserAuthority> selectUserAuthorityRequests() {
        return queryFactory
                .selectFrom(userAuthority)
//                .where(userAuthority.approval.eq(false))
                .fetch();
    }
}
