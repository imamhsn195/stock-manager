package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Product;
import me.imamhasan.stockmanager.model.ProductCategory;
import me.imamhasan.stockmanager.repository.ProductCategoryRepository;
import me.imamhasan.stockmanager.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
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
    public Product addProduct(Product product) {
        if(product.getProductCategory().getId() != null){
            Long productCategoryId = product.getProductCategory().getId();
            productCategoryRepository.findById(productCategoryId).orElseThrow(() -> new IllegalStateException("Product category not found with id " + productCategoryId));
        }else{
            ProductCategory productCategory = productCategoryRepository.save(product.getProductCategory());
            product.setProductCategory(productCategory);
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {

        System.out.println("Product: " + product);

        productRepository.findById(product.getId()).orElseThrow(() -> new IllegalStateException("Product with id " + product.getId() + ""));

        if(product.getProductCategory().getId() != null){
            Long productCategoryId = product.getProductCategory().getId();
            productCategoryRepository.findById(productCategoryId).orElseThrow(() -> new IllegalStateException("Product category not found with id " + productCategoryId));
        }else{
            ProductCategory productCategory = productCategoryRepository.save(product.getProductCategory());
            product.setProductCategory(productCategory);
        }

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product with id " + id + ""));
        productRepository.deleteById(id);
    }
}