package com.coco.bakingbuddy.ranking.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRankingTermCache is a Querydsl query type for RankingTermCache
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRankingTermCache extends EntityPathBase<RankingTermCache> {

    private static final long serialVersionUID = 238753067L;

    public static final QRankingTermCache rankingTermCache = new QRankingTermCache("rankingTermCache");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath term = createString("term");

    public QRankingTermCache(String variable) {
        super(RankingTermCache.class, forVariable(variable));
    }

    public QRankingTermCache(Path<? extends RankingTermCache> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRankingTermCache(PathMetadata metadata) {
        super(RankingTermCache.class, metadata);
    }

}

