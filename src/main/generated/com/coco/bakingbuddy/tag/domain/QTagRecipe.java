package com.coco.bakingbuddy.tag.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTagRecipe is a Querydsl query type for TagRecipe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTagRecipe extends EntityPathBase<TagRecipe> {

    private static final long serialVersionUID = 1334639225L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTagRecipe tagRecipe = new QTagRecipe("tagRecipe");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final com.coco.bakingbuddy.recipe.domain.QRecipe recipe;

    public final QTag tag;

    public QTagRecipe(String variable) {
        this(TagRecipe.class, forVariable(variable), INITS);
    }

    public QTagRecipe(Path<? extends TagRecipe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTagRecipe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTagRecipe(PathMetadata metadata, PathInits inits) {
        this(TagRecipe.class, metadata, inits);
    }

    public QTagRecipe(Class<? extends TagRecipe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recipe = inits.isInitialized("recipe") ? new com.coco.bakingbuddy.recipe.domain.QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

