package com.waszczun.sebastian.ecommerce.controller;

import com.waszczun.sebastian.ecommerce.dto.ProductResponse;
import com.waszczun.sebastian.ecommerce.mapper.ProductMapper;
import com.waszczun.sebastian.ecommerce.model.Product;
import com.waszczun.sebastian.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> response = productService.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {
        if (productService.findById(id)==null) {
            throw new RuntimeException("Product not found");
        }
        return ResponseEntity.ok(productMapper.toResponse(productService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product product1 = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        Product product1 = productService.updateProduct(id, product);
        return ResponseEntity.ok(product1);
    }
}
