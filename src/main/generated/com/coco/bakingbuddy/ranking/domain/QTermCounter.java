package com.coco.bakingbuddy.ranking.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTermCounter is a Querydsl query type for TermCounter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTermCounter extends EntityPathBase<TermCounter> {

    private static final long serialVersionUID = -693039419L;

    public static final QTermCounter termCounter = new QTermCounter("termCounter");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath term = createString("term");

    public QTermCounter(String variable) {
        super(TermCounter.class, forVariable(variable));
    }

    public QTermCounter(Path<? extends TermCounter> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTermCounter(PathMetadata metadata) {
        super(TermCounter.class, metadata);
    }

}

