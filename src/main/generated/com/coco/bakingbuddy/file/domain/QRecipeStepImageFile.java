package com.coco.bakingbuddy.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecipeStepImageFile is a Querydsl query type for RecipeStepImageFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipeStepImageFile extends EntityPathBase<RecipeStepImageFile> {

    private static final long serialVersionUID = 1857794174L;

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

    public final NumberPath<Long> recipeStepId = createNumber("recipeStepId", Long.class);

    public final StringPath uploadPath = createString("uploadPath");

    public final StringPath uuid = createString("uuid");

    public QRecipeStepImageFile(String variable) {
        super(RecipeStepImageFile.class, forVariable(variable));
    }

    public QRecipeStepImageFile(Path<? extends RecipeStepImageFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecipeStepImageFile(PathMetadata metadata) {
        super(RecipeStepImageFile.class, metadata);
    }

}

