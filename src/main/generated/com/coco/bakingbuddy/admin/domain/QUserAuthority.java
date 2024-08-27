package com.coco.bakingbuddy.admin.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAuthority is a Querydsl query type for UserAuthority
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAuthority extends EntityPathBase<UserAuthority> {

    private static final long serialVersionUID = 796837268L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAuthority userAuthority = new QUserAuthority("userAuthority");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    public final BooleanPath approval = createBoolean("approval");

    public final com.coco.bakingbuddy.user.domain.QUser approvalUser;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final EnumPath<com.coco.bakingbuddy.user.service.RoleType> roleType = createEnum("roleType", com.coco.bakingbuddy.user.service.RoleType.class);

    public final com.coco.bakingbuddy.user.domain.QUser user;

    public QUserAuthority(String variable) {
        this(UserAuthority.class, forVariable(variable), INITS);
    }

    public QUserAuthority(Path<? extends UserAuthority> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAuthority(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAuthority(PathMetadata metadata, PathInits inits) {
        this(UserAuthority.class, metadata, inits);
    }

    public QUserAuthority(Class<? extends UserAuthority> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approvalUser = inits.isInitialized("approvalUser") ? new com.coco.bakingbuddy.user.domain.QUser(forProperty("approvalUser")) : null;
        this.user = inits.isInitialized("user") ? new com.coco.bakingbuddy.user.domain.QUser(forProperty("user")) : null;
    }

}

