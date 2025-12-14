package com.waszczun.sebastian.ecommerce.mapper;

import com.waszczun.sebastian.ecommerce.dto.ProductRequest;
import com.waszczun.sebastian.ecommerce.dto.ProductResponse;
import com.waszczun.sebastian.ecommerce.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    //z DTO na encje - przy tworzeniu produktu
    public Product toEntity(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setCategory(productRequest.category());
        product.setPrice(productRequest.price());
        product.setStockQuantity(productRequest.stockQuantity());
        return product;
    }

    //z encji do DTO - przy wyswietlaniu
    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }
}
