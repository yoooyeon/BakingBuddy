package com.coco.bakingbuddy.admin.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMainRecipe is a Querydsl query type for MainRecipe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMainRecipe extends EntityPathBase<MainRecipe> {

    private static final long serialVersionUID = 1781010507L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMainRecipe mainRecipe = new QMainRecipe("mainRecipe");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isCurrent = createBoolean("isCurrent");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final com.coco.bakingbuddy.recipe.domain.QRecipe recipe;

    public QMainRecipe(String variable) {
        this(MainRecipe.class, forVariable(variable), INITS);
    }

    public QMainRecipe(Path<? extends MainRecipe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMainRecipe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMainRecipe(PathMetadata metadata, PathInits inits) {
        this(MainRecipe.class, metadata, inits);
    }

    public QMainRecipe(Class<? extends MainRecipe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recipe = inits.isInitialized("recipe") ? new com.coco.bakingbuddy.recipe.domain.QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
    }

}

