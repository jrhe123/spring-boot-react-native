package com.example.demo.service;

import com.example.demo.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();

    List<Product> findAllProductsByParams(int limit, int offset);

    Product findByName(String name);

    Product findById(Long id);

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long productId);

    Long numberOfProducts();
}
