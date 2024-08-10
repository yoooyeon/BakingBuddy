package com.coco.bakingbuddy.product.controller;

import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.global.response.ErrorResponse;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.product.dto.request.CreateProductRequestDto;
import com.coco.bakingbuddy.product.dto.request.EditProductRequestDto;
import com.coco.bakingbuddy.product.dto.response.CreateProductResponseDto;
import com.coco.bakingbuddy.product.dto.response.SelectProductResponseDto;
import com.coco.bakingbuddy.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.coco.bakingbuddy.global.response.ErrorResponse.toResponseEntity;
import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    /**
     * 모든 상품 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<List<SelectProductResponseDto>>> selectAll() {
        return toResponseEntity("상품 조회 성공", productService.selectAll());
    }

    /**
     * 상품 아이디로 조회
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse<SelectProductResponseDto>> selectById(@PathVariable("id") Long id) {
        return toResponseEntity("상품 아이디로 조회 성공", productService.selectById(id));
    }

    /**
     * 상품 생성
     *
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateProductResponseDto>> create(
            @Valid @RequestBody CreateProductRequestDto dto) {
        try {
            CreateProductResponseDto savedProduct = productService.create(dto);
            return toResponseEntity("상품 생성 성공", savedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 상품 수정
     *
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("{id}/edit")
    public ResponseEntity<SuccessResponse<CreateProductResponseDto>> edit(
            @PathVariable("id") Long id,
            @Valid @RequestBody EditProductRequestDto dto) {
        return toResponseEntity("상품 수정 성공", productService.edit(dto));
    }

    /**
     * 상품 삭제
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<? extends Object> delete(@PathVariable("id") Long id) {
        try {
            productService.delete(id);
            return toResponseEntity("상품 삭제 성공");
        }catch (CustomException e){
            return toResponseEntity(HttpStatus.BAD_REQUEST, "상품을 찾을 수 없습니다");
        }
    }
}
