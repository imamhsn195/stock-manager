package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SaleService {
    Sale saveSale(Sale sale);
    Page<Sale> getAllSales(Pageable pageable);
    Sale getSaleById(Long orderId);
    ResponseEntity deleteSale(Long orderId);
    Sale updateSale(Sale sale);
}