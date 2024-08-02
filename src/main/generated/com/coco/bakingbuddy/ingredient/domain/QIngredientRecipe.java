package com.coco.bakingbuddy.ingredient.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIngredientRecipe is a Querydsl query type for IngredientRecipe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIngredientRecipe extends EntityPathBase<IngredientRecipe> {

    private static final long serialVersionUID = -1716504813L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIngredientRecipe ingredientRecipe = new QIngredientRecipe("ingredientRecipe");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QIngredient ingredient;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final com.coco.bakingbuddy.recipe.domain.QRecipe recipe;

    public final NumberPath<Integer> servings = createNumber("servings", Integer.class);

    public final EnumPath<Unit> unit = createEnum("unit", Unit.class);

    public QIngredientRecipe(String variable) {
        this(IngredientRecipe.class, forVariable(variable), INITS);
    }

    public QIngredientRecipe(Path<? extends IngredientRecipe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIngredientRecipe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIngredientRecipe(PathMetadata metadata, PathInits inits) {
        this(IngredientRecipe.class, metadata, inits);
    }

    public QIngredientRecipe(Class<? extends IngredientRecipe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ingredient = inits.isInitialized("ingredient") ? new QIngredient(forProperty("ingredient")) : null;
        this.recipe = inits.isInitialized("recipe") ? new com.coco.bakingbuddy.recipe.domain.QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
    }

}

