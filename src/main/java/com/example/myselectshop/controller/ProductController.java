package com.example.myselectshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.myselectshop.dto.ProductMypriceRequestDto;
import com.example.myselectshop.dto.ProductRequestDto;
import com.example.myselectshop.dto.ProductResponseDto;
import com.example.myselectshop.security.UserDetailsImpl;
import com.example.myselectshop.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.createProduct(requestDto, userDetails.getUser());
    }

    @PutMapping("/products/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) {
        return productService.updateProduct(id, requestDto);
    }

    @GetMapping("/products")
    public Page<ProductResponseDto> getProducts(
        @RequestParam("page") int page, // 클라이언트에서는 1부터 시작하기 때문에 1을 빼줘야됨.
        @RequestParam("size") int size,
        @RequestParam("sortBy") String sortBy, // 정렬 항목
        @RequestParam("isAsc") boolean isAsc,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.getProducts(userDetails.getUser(), page - 1, size, sortBy, isAsc);
    }

    // 상품에 폴더 추가
    @PostMapping("/products/{productId}/folder")
    public void addFolder(
        @PathVariable Long productId,
        @RequestParam Long folderId, // form 형식으로 folder 의 Id를 받아옴
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        productService.addFolder(productId, folderId, userDetails.getUser());
    }

    @GetMapping("/folders/{folderId}/products")
    public Page<ProductResponseDto> getProductsInFolder(
        @PathVariable Long folderId,
        @RequestParam("page") int page, // 클라이언트에서는 1부터 시작하기 때문에 1을 빼줘야됨.
        @RequestParam("size") int size,
        @RequestParam("sortBy") String sortBy, // 정렬 항목
        @RequestParam("isAsc") boolean isAsc,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return productService.getProductsInFolder(
            folderId,
            page - 1,
            size,
            sortBy,
            isAsc,
            userDetails.getUser()
        );
    }
}
