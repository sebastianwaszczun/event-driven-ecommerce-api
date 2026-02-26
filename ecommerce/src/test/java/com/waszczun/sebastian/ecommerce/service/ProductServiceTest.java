package com.waszczun.sebastian.ecommerce.service;
import com.waszczun.sebastian.ecommerce.model.Product;
import com.waszczun.sebastian.ecommerce.repository.ProductRepository;
import com.waszczun.sebastian.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldReturnAllProducts(){
        //GIVEN
        Product product1 = new Product(1, "Computer", "Computer", "Electronics", new BigDecimal(100), 100);
        Product product2 = new Product(1, "Computer", "Computer", "Electronics", new BigDecimal(100), 100);
        List<Product> productList = List.of(product1, product2);
        when(productRepository.findAll()).thenReturn(productList);

        //WHEN
        List<Product> all = productService.findAll();

        //THEN
        assertEquals(2, all.size());
        verify(productRepository).findAll();
    }

    @Test
    void shouldReturnProductByIdWhenExists(){
        //GIVEN
        Product product1 = new Product(1, "Computer", "Computer", "Electronics", new BigDecimal(100), 100);
        when(productRepository.findById(1)).thenReturn(Optional.of(product1));

        //WHEN
        Product byId = productService.findById(1);

        //THEN
        assertEquals(product1, byId);
        verify(productRepository).findById(1);
    }

    @Test
    void shouldReturnNullWhenProductByIdDoesNotExist(){
        //GIVEN
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        //WHEN
        Product byId = productService.findById(1);

        //THEN
        assertNull(byId);
        verify(productRepository).findById(1);
    }

    @Test
    void shouldAddProduct(){
        //GIVEN
        Product product1 = new Product(1, "Computer", "Computer", "Electronics", new BigDecimal(100), 100);
        when(productRepository.save(product1)).thenReturn(product1);

        //WHEN
        Product product = productService.addProduct(product1);

        //THEN
        assertEquals(product1, product);
        verify(productRepository).save(product);
    }

    @Test
    void shouldUpdateProductWhenExists(){
        //GIVEN
        Product product1 = new Product(1, "Computer", "Computer", "Electronics", new BigDecimal(100), 100);
        when(productRepository.findById(1)).thenReturn(Optional.of(product1));
        when(productRepository.save(product1)).thenReturn(product1);

        //WHEN
        Product product = productService.updateProduct(1, product1);

        //THEN
        assertEquals(product1, product);
        verify(productRepository).findById(1);
        verify(productRepository).save(product);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingProduct(){
        //GIVEN
        Product product1 = new Product(1, "Computer", "Computer", "Electronics", new BigDecimal(100), 100);
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        //THEN
        assertThrows(RuntimeException.class, () -> productService.updateProduct(1, product1));
        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldDeleteProductWhenExists(){
        //GIVEN
        Product product1 = new Product(1, "Computer", "Computer", "Electronics", new BigDecimal(100), 100);
        when(productRepository.findById(1)).thenReturn(Optional.of(product1));

        //WHEN
        productService.deleteProduct(1);

        //THEN
        verify(productRepository).deleteById(1);
    }

    @Test
    void shouldNotDeleteProductWhenDoesNotExist(){
        //GIVEN
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        //WHEN
        productService.deleteProduct(1);
        //THEN
        verify(productRepository, never()).deleteById(any());
    }


}
