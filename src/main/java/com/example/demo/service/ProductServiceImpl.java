package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
//It is not necessary. You can use it, if you have multiple database operation in a single service method.
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    // list all
    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // list by params
    @Override
    public List<Product> findAllProductsByParams(int limit, int offset) {
        Pageable pageable = new OffsetBasedPageRequest(limit, offset);
        return productRepository.findAll(pageable).getContent();
    }

    // find by id
    @Override
    public Product findById(final Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // find by name
    @Override
    public Product findByName(final String name) {
        return productRepository.findByName(name).orElse(null);
    }

    // create
    @Override
    public Product saveProduct(final Product product) {
        return productRepository.save(product);
    }

    // save = create / update
    @Override
    public Product updateProduct(final Product product) {
        return productRepository.save(product);
    }

    // delete
    @Override
    public void deleteProduct(final Long productId) {
        productRepository.deleteById(productId);
    }

    // total number
    @Override
    public Long numberOfProducts() {
        return productRepository.count();
    }


}
