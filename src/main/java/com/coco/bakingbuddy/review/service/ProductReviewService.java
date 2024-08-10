package com.coco.bakingbuddy.review.service;

import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.product.domain.Product;
import com.coco.bakingbuddy.product.repository.ProductRepository;
import com.coco.bakingbuddy.review.domain.ProductReview;
import com.coco.bakingbuddy.review.dto.request.CreateProductReviewRequestDto;
import com.coco.bakingbuddy.review.dto.request.EditProductReviewRequestDto;
import com.coco.bakingbuddy.review.dto.response.CreateProductReviewResponseDto;
import com.coco.bakingbuddy.review.dto.response.SelectProductReviewResponseDto;
import com.coco.bakingbuddy.review.repository.ProductReviewRepository;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.global.error.ErrorCode.PRODUCT_NOT_FOUND;
import static com.coco.bakingbuddy.global.error.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductReviewService {
    private final ProductReviewRepository productReviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<SelectProductReviewResponseDto> selectAll(User user) {
        return productReviewRepository.findAll().stream().map(SelectProductReviewResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public SelectProductReviewResponseDto selectById(Long reviewId) {
        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_REVIEW_NOT_FOUND));
        return SelectProductReviewResponseDto.fromEntity(review);
    }

    public List<SelectProductReviewResponseDto> selectByUserId(User user) {
        return productReviewRepository.findByUser(user).stream()
                .map(SelectProductReviewResponseDto::fromEntity).collect(Collectors.toList());
    }


    public CreateProductReviewResponseDto create(Long userId, CreateProductReviewRequestDto dto) {
        ProductReview entity = CreateProductReviewRequestDto.toEntity(dto);
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
        entity.setUser(user);
        entity.setProduct(product);
        ProductReview save = productReviewRepository.save(entity);
        CreateProductReviewResponseDto result = CreateProductReviewResponseDto.fromEntity(save);
        result.setUser(SelectUserResponseDto.fromEntity(user));
        return result;
    }

    public CreateProductReviewResponseDto edit(EditProductReviewRequestDto dto, User user) {
        ProductReview review = productReviewRepository.findById(dto.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_REVIEW_NOT_FOUND));
        if (!review.getUser().equals(user)) {
            throw new CustomException(ErrorCode.REVIEW_USER_MISMATCH);
        }
        review = EditProductReviewRequestDto.toEntity(dto);
        productReviewRepository.save(review);
        return CreateProductReviewResponseDto.fromEntity(review);
    }

    public void delete(Long reviewId, User user) {
        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_REVIEW_NOT_FOUND));
        if (!review.getUser().equals(user)) {
            throw new CustomException(ErrorCode.REVIEW_USER_MISMATCH);
        }
        productReviewRepository.delete(review);
    }
}
