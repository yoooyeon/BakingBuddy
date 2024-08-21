package com.coco.bakingbuddy.product.repository;

import com.coco.bakingbuddy.product.dto.response.SelectProductResponseDto;
import com.coco.bakingbuddy.user.domain.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.product.domain.QProduct.product;
import static com.coco.bakingbuddy.user.domain.QUser.user;


@RequiredArgsConstructor
@Repository
public class ProductQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<SelectProductResponseDto> findByUserId(Long userId) {
        return queryFactory
                .select(Projections.constructor(SelectProductResponseDto.class,
                                product.id
                                , product.name
                                , product.price
                                , product.description
                                , product.productImageUrl
                                , product.user.username
                                , product.user.profileImageUrl
                        ))
                .from(product)
                .where(product.user.id.eq(userId))
                        .fetch();


    }
}
