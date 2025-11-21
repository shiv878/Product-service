package com.product.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.product.entity.Product;
import com.product.product.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/createProduct",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> CreateProduct(@RequestPart("product") String product,@RequestPart(value = "file", required = false) MultipartFile productImage  ) throws IOException {

        Product newProduct = new ObjectMapper().readValue(product,Product.class);
        if(productImage!=null && !productImage.isEmpty()) {
            String originalFilename = UUID.randomUUID()+ "_"+productImage.getOriginalFilename();

            Path path = Paths.get(uploadDir + originalFilename);
            Files.write(path, productImage.getBytes());
            String fileUrl = "/uploads/" + originalFilename;
            newProduct.setImage(originalFilename);
            newProduct.setUrl(fileUrl);

        }

        productService.create(newProduct);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/getAllProduct")
    public ResponseEntity<List<Product>> getAllProducts() {

      List<Product> all= productService.getAllProducts();
      if(all.size()==0 && all.isEmpty()) {
       throw new RuntimeException("products is not availables");
      }

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
     Product prod=  productService.getById(id);
     return new ResponseEntity<>(prod, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {

       Product upd=productService.update(id,product);
        return new ResponseEntity<>(upd, HttpStatus.OK);
    }

    @GetMapping("/serchProductByName/{name}")
    public ResponseEntity<List<Product>> searchProductByName(@PathVariable String name){
       List<Product> list=productService.serch(name);

       return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
