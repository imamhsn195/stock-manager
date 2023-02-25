package me.imamhasan.stockmanager.repository;

import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
