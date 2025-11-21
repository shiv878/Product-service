package com.product.product.dao;

import com.product.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductServiceRepository extends JpaRepository<Product,Long> {
    List<Product> findByName(String name);
}
