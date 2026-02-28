package com.waszczun.sebastian.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waszczun.sebastian.ecommerce.config.SecurityConfig;
import com.waszczun.sebastian.ecommerce.dto.ProductRequest;
import com.waszczun.sebastian.ecommerce.dto.ProductResponse;
import com.waszczun.sebastian.ecommerce.mapper.ProductMapper;
import com.waszczun.sebastian.ecommerce.model.Product;
import com.waszczun.sebastian.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(SecurityConfig.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    private ProductService productService;
    @MockitoBean
    private ProductMapper productMapper;

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() throws Exception{
        //GIVEN
        int productId = 1;
        Product mockProduct = new Product(1, "Laptop", "Mocny sprzet", "Elektronika", new BigDecimal(5000), 10);
        ProductResponse productResponse = new ProductResponse(productId, "Laptop", "Mocny sprzet","Elektronika", new BigDecimal(10), 5000);
        when(productService.findById(productId)).thenReturn(mockProduct);
        when(productMapper.toResponse(mockProduct)).thenReturn(productResponse);

        //THEN
        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.category").value("Elektronika"));
    }

    @Test
    void deleteProductById_ShouldReturnNoContent_WhenProductDeleted() throws Exception{
        //GIVEN
        int productId = 1;

        //THEN
        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() throws Exception{
        //GIVEN
        Product mockProduct1= new Product(1, "Laptop1", "Mocny sprzet", "Elektronika", new BigDecimal(5000), 10);
        Product mockProduct2 = new Product(2, "Laptop2", "Mocny sprzet", "Elektronika", new BigDecimal(5000), 10);
        List<Product> productList = List.of(mockProduct1, mockProduct2);
        ProductResponse productResponse1 = new ProductResponse(1, "Laptop", "Mocny sprzet","Elektronika", new BigDecimal(10), 5000);
        ProductResponse productResponse2 = new ProductResponse(2, "Laptop", "Mocny sprzet","Elektronika", new BigDecimal(10), 5000);
        List<ProductResponse> responseList = List.of(productResponse2, productResponse1);
        when(productService.findAll()).thenReturn(productList);
        when(productMapper.toResponse(mockProduct1)).thenReturn(productResponse1);

        //THEN
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(responseList.getFirst().name()));

    }

    @Test
    void getAllProducts_ShouldReturnException() throws Exception{
        //GIVEN
        int productId = 99;
        when(productService.findById(productId)).thenReturn(null);

        //THEN
        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Product not found"));
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception{
        //GIVEN
        ProductRequest productRequest = new ProductRequest("Laptop", "Mocny sprzet", "Elektronika", new BigDecimal("5000.00"), 10);
        Product product = new Product(1, "Laptop", "Mocny sprzet", "Elektronika", new BigDecimal("5000.00"), 10);
        ProductResponse productResponse = new ProductResponse(1, "Laptop", "Mocny sprzet", "Elektronika", new BigDecimal("5000.00"), 10);
        when(productMapper.toEntity(any(ProductRequest.class))).thenReturn(product);
        when(productService.addProduct(product)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        //THEN
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }
    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception{
        //GIVEN
        int productId = 1;
        ProductRequest productRequest = new ProductRequest("Laptop", "Mocny sprzet", "Elektronika", new BigDecimal("5000.00"), 10);
        Product product = new Product(productId, "Laptop", "Mocny sprzet", "Elektronika", new BigDecimal("5000.00"), 10);
        Product product2 = new Product(productId, "Komputer", "Mocny sprzet", "Elektronika", new BigDecimal("5000.00"), 10);
        ProductResponse productResponse = new ProductResponse(1, "Komputer", "Mocny sprzet", "Elektronika", new BigDecimal("5000.00"), 10);
        when(productMapper.toEntity(any(ProductRequest.class))).thenReturn(product);
        when(productService.updateProduct(productId, product)).thenReturn(product2);
        when(productMapper.toResponse(product2)).thenReturn(productResponse);

        //THEN
        mockMvc.perform(put("/api/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(productRequest)))
                .andExpect(status().isOk());
    }

}
