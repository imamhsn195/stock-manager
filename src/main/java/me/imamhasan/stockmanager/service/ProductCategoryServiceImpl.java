package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.ProductCategory;
import me.imamhasan.stockmanager.repository.ProductCategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public Page<ProductCategory> getAllProductCategories(Pageable pageable) {
        return productCategoryRepository.findAll(pageable);
    }

    @Override
    public ProductCategory getProductCategoryById(Long productCategoryId) {
        return productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new IllegalStateException("ProductCategory with id " + productCategoryId + " not found"));
    }

    @Override
    public void deleteProductCategory(Long productCategoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new IllegalStateException("ProductCategory with id " + productCategoryId + " not found"));
        productCategoryRepository.delete(productCategory);
    }

    @Override
    public ProductCategory updateProductCategory(ProductCategory productCategory) {
        productCategoryRepository.findById(productCategory.getId())
                .orElseThrow(() -> new IllegalStateException("ProductCategory with id " + productCategory.getId() + " not found"));
        return productCategoryRepository.save(productCategory);
    }
}