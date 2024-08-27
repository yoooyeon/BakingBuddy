package com.coco.bakingbuddy.admin.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = 1501724376L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReport report = new QReport("report");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    public final com.coco.bakingbuddy.user.domain.QUser completeAdmin;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isCompleted = createBoolean("isCompleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath reason = createString("reason");

    public final com.coco.bakingbuddy.user.domain.QUser reported;

    public final com.coco.bakingbuddy.user.domain.QUser reporter;

    public final EnumPath<ReportType> reportType = createEnum("reportType", ReportType.class);

    public QReport(String variable) {
        this(Report.class, forVariable(variable), INITS);
    }

    public QReport(Path<? extends Report> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReport(PathMetadata metadata, PathInits inits) {
        this(Report.class, metadata, inits);
    }

    public QReport(Class<? extends Report> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.completeAdmin = inits.isInitialized("completeAdmin") ? new com.coco.bakingbuddy.user.domain.QUser(forProperty("completeAdmin")) : null;
        this.reported = inits.isInitialized("reported") ? new com.coco.bakingbuddy.user.domain.QUser(forProperty("reported")) : null;
        this.reporter = inits.isInitialized("reporter") ? new com.coco.bakingbuddy.user.domain.QUser(forProperty("reporter")) : null;
    }

}

