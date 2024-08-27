package com.coco.bakingbuddy.recipe.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipeStep is a Querydsl query type for RecipeStep
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipeStep extends EntityPathBase<RecipeStep> {

    private static final long serialVersionUID = -1831156021L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipeStep recipeStep = new QRecipeStep("recipeStep");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.coco.bakingbuddy.file.domain.RecipeStepImageFile, com.coco.bakingbuddy.file.domain.QRecipeStepImageFile> imageFiles = this.<com.coco.bakingbuddy.file.domain.RecipeStepImageFile, com.coco.bakingbuddy.file.domain.QRecipeStepImageFile>createList("imageFiles", com.coco.bakingbuddy.file.domain.RecipeStepImageFile.class, com.coco.bakingbuddy.file.domain.QRecipeStepImageFile.class, PathInits.DIRECT2);

    public final QRecipe recipe;

    public final StringPath stepImage = createString("stepImage");

    public final NumberPath<Integer> stepNumber = createNumber("stepNumber", Integer.class);

    public QRecipeStep(String variable) {
        this(RecipeStep.class, forVariable(variable), INITS);
    }

    public QRecipeStep(Path<? extends RecipeStep> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipeStep(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipeStep(PathMetadata metadata, PathInits inits) {
        this(RecipeStep.class, metadata, inits);
    }

    public QRecipeStep(Class<? extends RecipeStep> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recipe = inits.isInitialized("recipe") ? new QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
    }

}

