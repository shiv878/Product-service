package com.product.product.service;

import com.product.product.entity.Product;

import java.util.List;

public interface ProductService {
    void create(Product newProduct);

    List<Product> getAllProducts();

    Product getById(Long id);

    Product update(Long id, Product product);

    List<Product> serch(String name);
}
