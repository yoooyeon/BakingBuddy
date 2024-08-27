package com.coco.bakingbuddy.search.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClickRecord is a Querydsl query type for ClickRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClickRecord extends EntityPathBase<ClickRecord> {

    private static final long serialVersionUID = 1561971310L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClickRecord clickRecord = new QClickRecord("clickRecord");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    public final EnumPath<ClickType> clickType = createEnum("clickType", ClickType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> entityId = createNumber("entityId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final com.coco.bakingbuddy.user.domain.QUser user;

    public QClickRecord(String variable) {
        this(ClickRecord.class, forVariable(variable), INITS);
    }

    public QClickRecord(Path<? extends ClickRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClickRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClickRecord(PathMetadata metadata, PathInits inits) {
        this(ClickRecord.class, metadata, inits);
    }

    public QClickRecord(Class<? extends ClickRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.coco.bakingbuddy.user.domain.QUser(forProperty("user")) : null;
    }

}

