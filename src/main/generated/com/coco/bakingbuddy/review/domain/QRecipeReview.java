package com.coco.bakingbuddy.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipeReview is a Querydsl query type for RecipeReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipeReview extends EntityPathBase<RecipeReview> {

    private static final long serialVersionUID = 333679713L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipeReview recipeReview = new QRecipeReview("recipeReview");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.coco.bakingbuddy.recipe.domain.QRecipe recipe;

    public final com.coco.bakingbuddy.user.domain.QUser user;

    public QRecipeReview(String variable) {
        this(RecipeReview.class, forVariable(variable), INITS);
    }

    public QRecipeReview(Path<? extends RecipeReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipeReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipeReview(PathMetadata metadata, PathInits inits) {
        this(RecipeReview.class, metadata, inits);
    }

    public QRecipeReview(Class<? extends RecipeReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recipe = inits.isInitialized("recipe") ? new com.coco.bakingbuddy.recipe.domain.QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
        this.user = inits.isInitialized("user") ? new com.coco.bakingbuddy.user.domain.QUser(forProperty("user")) : null;
    }

}

