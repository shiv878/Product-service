package com.product.product.serviceIMPL;

import com.product.product.dao.ProductServiceRepository;
import com.product.product.entity.Product;
import com.product.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceIMPL implements ProductService {

    private final ProductServiceRepository productServiceRepository;

    public ProductServiceIMPL(ProductServiceRepository productServiceRepository) {
        this.productServiceRepository = productServiceRepository;
    }

    @Override
    public void create(Product newProduct) {
        if(newProduct.getId()!=null){
            productServiceRepository.save(newProduct);
        }
        productServiceRepository.save(newProduct);
    }

    @Override
    public List<Product> getAllProducts() {

        return productServiceRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productServiceRepository.findById(id).orElseThrow(()->new RuntimeException("product not found for id="+id));
    }

    @Override
    public Product update(Long id, Product product) {
        Product byId = getById(id);
        byId.setName(product.getName());
        byId.setPrice(product.getPrice());
        byId.setCategory(product.getCategory());
        byId.setDescription(product.getDescription());
        byId.setQuantity(product.getQuantity());
        create(byId);

        return byId;
    }

    @Override
    public List<Product> serch(String name) {
        return productServiceRepository.findByName(name);
    }
}
