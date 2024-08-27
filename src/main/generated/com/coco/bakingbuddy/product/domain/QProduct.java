package com.coco.bakingbuddy.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1448201323L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath link = createString("link");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final ListPath<String, StringPath> productDetailImageUrls = this.<String, StringPath>createList("productDetailImageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<com.coco.bakingbuddy.file.domain.ProductImageFile, com.coco.bakingbuddy.file.domain.QProductImageFile> productImageFiles = this.<com.coco.bakingbuddy.file.domain.ProductImageFile, com.coco.bakingbuddy.file.domain.QProductImageFile>createList("productImageFiles", com.coco.bakingbuddy.file.domain.ProductImageFile.class, com.coco.bakingbuddy.file.domain.QProductImageFile.class, PathInits.DIRECT2);

    public final StringPath productImageUrl = createString("productImageUrl");

    public final NumberPath<Long> providerId = createNumber("providerId", Long.class);

    public final ListPath<com.coco.bakingbuddy.review.domain.ProductReview, com.coco.bakingbuddy.review.domain.QProductReview> reviews = this.<com.coco.bakingbuddy.review.domain.ProductReview, com.coco.bakingbuddy.review.domain.QProductReview>createList("reviews", com.coco.bakingbuddy.review.domain.ProductReview.class, com.coco.bakingbuddy.review.domain.QProductReview.class, PathInits.DIRECT2);

    public final com.coco.bakingbuddy.user.domain.QUser user;

    public final BooleanPath useYn = createBoolean("useYn");

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.coco.bakingbuddy.user.domain.QUser(forProperty("user")) : null;
    }

}

