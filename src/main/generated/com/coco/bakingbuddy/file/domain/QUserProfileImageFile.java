package com.coco.bakingbuddy.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserProfileImageFile is a Querydsl query type for UserProfileImageFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserProfileImageFile extends EntityPathBase<UserProfileImageFile> {

    private static final long serialVersionUID = 350356600L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserProfileImageFile userProfileImageFile = new QUserProfileImageFile("userProfileImageFile");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath ext = createString("ext");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath originalName = createString("originalName");

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final StringPath uploadPath = createString("uploadPath");

    public final com.coco.bakingbuddy.user.domain.QUser user;

    public final StringPath uuid = createString("uuid");

    public QUserProfileImageFile(String variable) {
        this(UserProfileImageFile.class, forVariable(variable), INITS);
    }

    public QUserProfileImageFile(Path<? extends UserProfileImageFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserProfileImageFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserProfileImageFile(PathMetadata metadata, PathInits inits) {
        this(UserProfileImageFile.class, metadata, inits);
    }

    public QUserProfileImageFile(Class<? extends UserProfileImageFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.coco.bakingbuddy.user.domain.QUser(forProperty("user")) : null;
    }

}

