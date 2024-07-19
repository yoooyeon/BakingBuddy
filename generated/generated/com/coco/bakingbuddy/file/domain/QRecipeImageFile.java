package com.coco.bakingbuddy.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecipeImageFile is a Querydsl query type for RecipeImageFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipeImageFile extends EntityPathBase<RecipeImageFile> {

    private static final long serialVersionUID = 112517994L;

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

    public final NumberPath<Long> recipeId = createNumber("recipeId", Long.class);

    public final StringPath uploadPath = createString("uploadPath");

    public final StringPath uuid = createString("uuid");

    public QRecipeImageFile(String variable) {
        super(RecipeImageFile.class, forVariable(variable));
    }

    public QRecipeImageFile(Path<? extends RecipeImageFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecipeImageFile(PathMetadata metadata) {
        super(RecipeImageFile.class, metadata);
    }

}

