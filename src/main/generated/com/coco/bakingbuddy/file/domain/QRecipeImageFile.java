package com.coco.bakingbuddy.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipeImageFile is a Querydsl query type for RecipeImageFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipeImageFile extends EntityPathBase<RecipeImageFile> {

    private static final long serialVersionUID = 112517994L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipeImageFile recipeImageFile = new QRecipeImageFile("recipeImageFile");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath ext = createString("ext");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath originalName = createString("originalName");

    public final com.coco.bakingbuddy.recipe.domain.QRecipe recipe;

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final StringPath uploadPath = createString("uploadPath");

    public final StringPath uuid = createString("uuid");

    public QRecipeImageFile(String variable) {
        this(RecipeImageFile.class, forVariable(variable), INITS);
    }

    public QRecipeImageFile(Path<? extends RecipeImageFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipeImageFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipeImageFile(PathMetadata metadata, PathInits inits) {
        this(RecipeImageFile.class, metadata, inits);
    }

    public QRecipeImageFile(Class<? extends RecipeImageFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recipe = inits.isInitialized("recipe") ? new com.coco.bakingbuddy.recipe.domain.QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
    }

}

