package com.coco.bakingbuddy.recommendation.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductRecommendation is a Querydsl query type for ProductRecommendation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductRecommendation extends EntityPathBase<ProductRecommendation> {

    private static final long serialVersionUID = -1490980532L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductRecommendation productRecommendation = new QProductRecommendation("productRecommendation");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final com.coco.bakingbuddy.product.domain.QProduct product;

    public final com.coco.bakingbuddy.recipe.domain.QRecipe recipe;

    public final StringPath recommendationReason = createString("recommendationReason");

    public QProductRecommendation(String variable) {
        this(ProductRecommendation.class, forVariable(variable), INITS);
    }

    public QProductRecommendation(Path<? extends ProductRecommendation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductRecommendation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductRecommendation(PathMetadata metadata, PathInits inits) {
        this(ProductRecommendation.class, metadata, inits);
    }

    public QProductRecommendation(Class<? extends ProductRecommendation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.coco.bakingbuddy.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
        this.recipe = inits.isInitialized("recipe") ? new com.coco.bakingbuddy.recipe.domain.QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
    }

}

