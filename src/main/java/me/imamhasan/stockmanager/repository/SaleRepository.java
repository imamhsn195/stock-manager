package me.imamhasan.stockmanager.repository;

import me.imamhasan.stockmanager.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
