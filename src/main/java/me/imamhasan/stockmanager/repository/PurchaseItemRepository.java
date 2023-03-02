package me.imamhasan.stockmanager.repository;

import me.imamhasan.stockmanager.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
}
