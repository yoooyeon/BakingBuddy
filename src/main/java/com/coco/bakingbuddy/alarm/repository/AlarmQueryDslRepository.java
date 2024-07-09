package com.coco.bakingbuddy.alarm.repository;

import com.coco.bakingbuddy.alarm.domain.Alarm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.alarm.domain.QAlarm.alarm;

@RequiredArgsConstructor
@Repository
public class AlarmQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<Alarm> findByUserId(Long userId) {
        return queryFactory
                .selectFrom(alarm)
                .where(alarm.user.id.eq(userId))
                .fetch();
    }

}
