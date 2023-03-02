package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.Product;
import me.imamhasan.stockmanager.model.ProductCategory;
import me.imamhasan.stockmanager.repository.ProductCategoryRepository;
import me.imamhasan.stockmanager.repository.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Product with id " + id + " not found."));
    }

    @Override
    public Product addProduct(@NotNull Product product) {
        if(product.getProductCategory().getId() != null){
            Long productCategoryId = product.getProductCategory().getId();
            productCategoryRepository.findById(productCategoryId).orElseThrow(() -> new IllegalStateException("Product category not found with id " + productCategoryId));
        }else{
            ProductCategory productCategory = productCategoryRepository.save(product.getProductCategory());
            product.setProductCategory(productCategory);
        }
        product.setQuantity(0);
        return productRepository.save(product);
    }
    @Override
    public ResponseEntity deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product with id " + productId + ""));
        productRepository.delete(product);
        return ResponseEntity.ok().body("Product deleted successfully.");
    }
    @Override
    public Product updateProduct(@NotNull Product product) {
        Integer previousQty = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Product with id " + product.getId() + "")).getQuantity();
        if(product.getProductCategory().getId() != null){
            Long productCategoryId = product.getProductCategory().getId();
            productCategoryRepository.findById(productCategoryId).orElseThrow(() -> new IllegalStateException("Product category not found with id " + productCategoryId));
        }else{
            ProductCategory productCategory = productCategoryRepository.save(product.getProductCategory());
            product.setProductCategory(productCategory);
        }
        product.setQuantity(previousQty);
        return productRepository.save(product);
    }

}