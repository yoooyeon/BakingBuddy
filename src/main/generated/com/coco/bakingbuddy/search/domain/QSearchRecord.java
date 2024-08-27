package com.coco.bakingbuddy.search.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSearchRecord is a Querydsl query type for SearchRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSearchRecord extends EntityPathBase<SearchRecord> {

    private static final long serialVersionUID = -384156604L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSearchRecord searchRecord = new QSearchRecord("searchRecord");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath term = createString("term");

    public final DateTimePath<java.time.LocalDateTime> timestamp = createDateTime("timestamp", java.time.LocalDateTime.class);

    public final com.coco.bakingbuddy.user.domain.QUser user;

    public QSearchRecord(String variable) {
        this(SearchRecord.class, forVariable(variable), INITS);
    }

    public QSearchRecord(Path<? extends SearchRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSearchRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSearchRecord(PathMetadata metadata, PathInits inits) {
        this(SearchRecord.class, metadata, inits);
    }

    public QSearchRecord(Class<? extends SearchRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.coco.bakingbuddy.user.domain.QUser(forProperty("user")) : null;
    }

}

