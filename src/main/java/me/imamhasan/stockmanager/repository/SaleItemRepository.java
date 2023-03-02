package me.imamhasan.stockmanager.repository;

import me.imamhasan.stockmanager.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}
