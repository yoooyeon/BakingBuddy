package com.coco.bakingbuddy.recipe.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIngredient is a Querydsl query type for Ingredient
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIngredient extends EntityPathBase<Ingredient> {

    private static final long serialVersionUID = -1686053086L;

    public static final QIngredient ingredient = new QIngredient("ingredient");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<IngredientRecipe, QIngredientRecipe> ingredientRecipes = this.<IngredientRecipe, QIngredientRecipe>createList("ingredientRecipes", IngredientRecipe.class, QIngredientRecipe.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public QIngredient(String variable) {
        super(Ingredient.class, forVariable(variable));
    }

    public QIngredient(Path<? extends Ingredient> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIngredient(PathMetadata metadata) {
        super(Ingredient.class, metadata);
    }

}

