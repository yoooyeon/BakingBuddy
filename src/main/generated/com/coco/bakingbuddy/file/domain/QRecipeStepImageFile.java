package com.coco.bakingbuddy.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipeStepImageFile is a Querydsl query type for RecipeStepImageFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipeStepImageFile extends EntityPathBase<RecipeStepImageFile> {

    private static final long serialVersionUID = 1857794174L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipeStepImageFile recipeStepImageFile = new QRecipeStepImageFile("recipeStepImageFile");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath ext = createString("ext");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath originalName = createString("originalName");

    public final com.coco.bakingbuddy.recipe.domain.QRecipeStep recipeStep;

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final StringPath uploadPath = createString("uploadPath");

    public final StringPath uuid = createString("uuid");

    public QRecipeStepImageFile(String variable) {
        this(RecipeStepImageFile.class, forVariable(variable), INITS);
    }

    public QRecipeStepImageFile(Path<? extends RecipeStepImageFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipeStepImageFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipeStepImageFile(PathMetadata metadata, PathInits inits) {
        this(RecipeStepImageFile.class, metadata, inits);
    }

    public QRecipeStepImageFile(Class<? extends RecipeStepImageFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recipeStep = inits.isInitialized("recipeStep") ? new com.coco.bakingbuddy.recipe.domain.QRecipeStep(forProperty("recipeStep"), inits.get("recipeStep")) : null;
    }

}

