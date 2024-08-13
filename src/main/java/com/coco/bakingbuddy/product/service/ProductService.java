package com.coco.bakingbuddy.product.service;

import com.coco.bakingbuddy.file.service.FileService;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.product.domain.Product;
import com.coco.bakingbuddy.product.dto.request.CreateProductRequestDto;
import com.coco.bakingbuddy.product.dto.request.EditProductRequestDto;
import com.coco.bakingbuddy.product.dto.response.CreateProductResponseDto;
import com.coco.bakingbuddy.product.dto.response.SelectProductResponseDto;
import com.coco.bakingbuddy.product.repository.ProductQueryDslRepository;
import com.coco.bakingbuddy.product.repository.ProductRepository;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.global.error.ErrorCode.PRODUCT_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductQueryDslRepository productQueryDslRepository;
    private final FileService fileService;

    @Transactional(readOnly = true)

    public List<SelectProductResponseDto> selectAll() {
        return productRepository.findAll().stream().map(SelectProductResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SelectProductResponseDto selectById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
        return SelectProductResponseDto.fromEntity(product);
    }

    @Transactional
    public CreateProductResponseDto create(CreateProductRequestDto dto, User user) {
        Product product = CreateProductRequestDto.toEntity(dto);
        product.setUser(user);
        product = productRepository.save(product);

        try {
            if (dto.getProductImage() != null) {
                String imageUrl = fileService.uploadProductImage(product.getId(), dto.getProductImage());
                product.setImageUrl(imageUrl);
                productRepository.save(product); // 이미지 URL 업데이트 후 상품 재저장
            }
        } catch (Exception e) {
            System.err.println("이미지 업로드 실패: " + e.getMessage());
            // 예외를 던지지 않으면 트랜잭션이 롤백되지 않습니다.
        }

        return CreateProductResponseDto.fromEntity(product);
    }


    @Transactional

    public CreateProductResponseDto edit(EditProductRequestDto dto) {
        Product product = EditProductRequestDto.toEntity(dto);
        return CreateProductResponseDto.fromEntity(productRepository.save(product)); // 명시
    }

    @Transactional

    public void delete(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
        productRepository.delete(product);
    }

    public List<SelectProductResponseDto> selectProductsByUserId(Long id) {
        return productQueryDslRepository.findByUserId(id);
    }
}
