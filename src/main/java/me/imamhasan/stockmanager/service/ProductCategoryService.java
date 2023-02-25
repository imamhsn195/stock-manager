package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductCategoryService {
    ProductCategory saveProductCategory(ProductCategory productCategory);
    Page<ProductCategory> getAllProductCategories(Pageable pageable);
    ProductCategory getProductCategoryById(Long productCategoryId);
    void deleteProductCategory(Long productCategoryId);
    ProductCategory updateProductCategory(ProductCategory productCategory);
}