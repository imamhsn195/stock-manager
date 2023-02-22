package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Product;

import java.util.List;


public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product addProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long id);
}