package com.waszczun.sebastian.ecommerce.repository;

import com.waszczun.sebastian.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
