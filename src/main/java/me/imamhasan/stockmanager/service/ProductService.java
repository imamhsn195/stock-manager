package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);
    Product getProductById(Long id);
    Product addProduct(Product product);
    Product updateProduct(Product product);
    ResponseEntity deleteProduct(Long id);


}