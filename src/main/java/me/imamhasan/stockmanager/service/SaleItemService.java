package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Sale;
import me.imamhasan.stockmanager.model.SaleItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SaleItemService {
    SaleItem saveSaleItem(SaleItem saleItem);
    Page<SaleItem> getAllSaleItems(Pageable pageable);
    SaleItem getSaleItemById(Long saleItemId);
    ResponseEntity deleteSaleItem(Long saleItemId);
    SaleItem updateSaleItem(SaleItem saleItem);
}