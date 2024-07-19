package com.coco.bakingbuddy.search.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecentSearch is a Querydsl query type for RecentSearch
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecentSearch extends EntityPathBase<RecentSearch> {

    private static final long serialVersionUID = 900563982L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecentSearch recentSearch = new QRecentSearch("recentSearch");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath term = createString("term");

    public final DateTimePath<java.time.LocalDateTime> timestamp = createDateTime("timestamp", java.time.LocalDateTime.class);

    public final com.coco.bakingbuddy.user.domain.QUser user;

    public QRecentSearch(String variable) {
        this(RecentSearch.class, forVariable(variable), INITS);
    }

    public QRecentSearch(Path<? extends RecentSearch> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecentSearch(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecentSearch(PathMetadata metadata, PathInits inits) {
        this(RecentSearch.class, metadata, inits);
    }

    public QRecentSearch(Class<? extends RecentSearch> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.coco.bakingbuddy.user.domain.QUser(forProperty("user")) : null;
    }

}

