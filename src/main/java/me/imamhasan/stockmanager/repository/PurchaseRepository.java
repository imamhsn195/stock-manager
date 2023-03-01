package me.imamhasan.stockmanager.repository;

import me.imamhasan.stockmanager.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
