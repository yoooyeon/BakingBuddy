package com.coco.bakingbuddy.recipe.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipe is a Querydsl query type for Recipe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipe extends EntityPathBase<Recipe> {

    private static final long serialVersionUID = 1857688415L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipe recipe = new QRecipe("recipe");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final QDirectory directory;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.coco.bakingbuddy.file.domain.RecipeImageFile, com.coco.bakingbuddy.file.domain.QRecipeImageFile> imageFiles = this.<com.coco.bakingbuddy.file.domain.RecipeImageFile, com.coco.bakingbuddy.file.domain.QRecipeImageFile>createList("imageFiles", com.coco.bakingbuddy.file.domain.RecipeImageFile.class, com.coco.bakingbuddy.file.domain.QRecipeImageFile.class, PathInits.DIRECT2);

    public final ListPath<com.coco.bakingbuddy.ingredient.domain.IngredientRecipe, com.coco.bakingbuddy.ingredient.domain.QIngredientRecipe> ingredientRecipes = this.<com.coco.bakingbuddy.ingredient.domain.IngredientRecipe, com.coco.bakingbuddy.ingredient.domain.QIngredientRecipe>createList("ingredientRecipes", com.coco.bakingbuddy.ingredient.domain.IngredientRecipe.class, com.coco.bakingbuddy.ingredient.domain.QIngredientRecipe.class, PathInits.DIRECT2);

    public final StringPath level = createString("level");

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final SetPath<com.coco.bakingbuddy.like.domain.Like, com.coco.bakingbuddy.like.domain.QLike> likes = this.<com.coco.bakingbuddy.like.domain.Like, com.coco.bakingbuddy.like.domain.QLike>createSet("likes", com.coco.bakingbuddy.like.domain.Like.class, com.coco.bakingbuddy.like.domain.QLike.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath openYn = createString("openYn");

    public final StringPath recipeImageUrl = createString("recipeImageUrl");

    public final ListPath<RecipeStep, QRecipeStep> recipeSteps = this.<RecipeStep, QRecipeStep>createList("recipeSteps", RecipeStep.class, QRecipeStep.class, PathInits.DIRECT2);

    public final ListPath<com.coco.bakingbuddy.review.domain.RecipeReview, com.coco.bakingbuddy.review.domain.QRecipeReview> reviews = this.<com.coco.bakingbuddy.review.domain.RecipeReview, com.coco.bakingbuddy.review.domain.QRecipeReview>createList("reviews", com.coco.bakingbuddy.review.domain.RecipeReview.class, com.coco.bakingbuddy.review.domain.QRecipeReview.class, PathInits.DIRECT2);

    public final SetPath<com.coco.bakingbuddy.tag.domain.TagRecipe, com.coco.bakingbuddy.tag.domain.QTagRecipe> tagRecipes = this.<com.coco.bakingbuddy.tag.domain.TagRecipe, com.coco.bakingbuddy.tag.domain.QTagRecipe>createSet("tagRecipes", com.coco.bakingbuddy.tag.domain.TagRecipe.class, com.coco.bakingbuddy.tag.domain.QTagRecipe.class, PathInits.DIRECT2);

    public final NumberPath<Integer> time = createNumber("time", Integer.class);

    public final com.coco.bakingbuddy.user.domain.QUser user;

    public final BooleanPath useYn = createBoolean("useYn");

    public QRecipe(String variable) {
        this(Recipe.class, forVariable(variable), INITS);
    }

    public QRecipe(Path<? extends Recipe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipe(PathMetadata metadata, PathInits inits) {
        this(Recipe.class, metadata, inits);
    }

    public QRecipe(Class<? extends Recipe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.directory = inits.isInitialized("directory") ? new QDirectory(forProperty("directory"), inits.get("directory")) : null;
        this.user = inits.isInitialized("user") ? new com.coco.bakingbuddy.user.domain.QUser(forProperty("user")) : null;
    }

}

