package com.coco.bakingbuddy.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductImageFile is a Querydsl query type for ProductImageFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductImageFile extends EntityPathBase<ProductImageFile> {

    private static final long serialVersionUID = 1549471111L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductImageFile productImageFile = new QProductImageFile("productImageFile");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath ext = createString("ext");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath originalName = createString("originalName");

    public final com.coco.bakingbuddy.product.domain.QProduct product;

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final StringPath uploadPath = createString("uploadPath");

    public final StringPath uuid = createString("uuid");

    public QProductImageFile(String variable) {
        this(ProductImageFile.class, forVariable(variable), INITS);
    }

    public QProductImageFile(Path<? extends ProductImageFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductImageFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductImageFile(PathMetadata metadata, PathInits inits) {
        this(ProductImageFile.class, metadata, inits);
    }

    public QProductImageFile(Class<? extends ProductImageFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.coco.bakingbuddy.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

