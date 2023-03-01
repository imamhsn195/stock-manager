package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PurchaseService {
    Purchase savePurchase(Purchase purchase);
    Page<Purchase> getAllPurchases(Pageable pageable);
    Purchase getPurchaseById(Long purchaseId);
    ResponseEntity deletePurchase(Long purchaseId);
    Purchase updatePurchase(Purchase purchase);
}