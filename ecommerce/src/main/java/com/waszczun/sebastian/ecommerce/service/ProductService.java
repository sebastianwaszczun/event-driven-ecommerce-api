package com.waszczun.sebastian.ecommerce.service;

import com.waszczun.sebastian.ecommerce.model.Product;
import com.waszczun.sebastian.ecommerce.repository.ProductRepository;
import com.waszczun.sebastian.ecommerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
}
