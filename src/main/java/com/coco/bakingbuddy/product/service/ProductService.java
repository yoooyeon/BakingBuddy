package com.coco.bakingbuddy.product.service;

import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.product.domain.Product;
import com.coco.bakingbuddy.product.dto.request.CreateProductRequestDto;
import com.coco.bakingbuddy.product.dto.request.EditProductRequestDto;
import com.coco.bakingbuddy.product.dto.response.CreateProductResponseDto;
import com.coco.bakingbuddy.product.dto.response.SelectProductResponseDto;
import com.coco.bakingbuddy.product.repository.ProductRepository;
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
    public CreateProductResponseDto create(CreateProductRequestDto dto) {
        Product product = CreateProductRequestDto.toEntity(dto);
        product = productRepository.save(product);
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
}
