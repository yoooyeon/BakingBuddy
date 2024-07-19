package com.coco.bakingbuddy.recipe.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDirectory is a Querydsl query type for Directory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDirectory extends EntityPathBase<Directory> {

    private static final long serialVersionUID = -40484100L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDirectory directory = new QDirectory("directory");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final ListPath<Recipe, QRecipe> recipes = this.<Recipe, QRecipe>createList("recipes", Recipe.class, QRecipe.class, PathInits.DIRECT2);

    public final com.coco.bakingbuddy.user.domain.QUser user;

    public final BooleanPath useYn = createBoolean("useYn");

    public QDirectory(String variable) {
        this(Directory.class, forVariable(variable), INITS);
    }

    public QDirectory(Path<? extends Directory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDirectory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDirectory(PathMetadata metadata, PathInits inits) {
        this(Directory.class, metadata, inits);
    }

    public QDirectory(Class<? extends Directory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.coco.bakingbuddy.user.domain.QUser(forProperty("user")) : null;
    }

}

