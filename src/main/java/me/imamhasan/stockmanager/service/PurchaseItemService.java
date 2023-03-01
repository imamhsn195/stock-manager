package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.PurchaseItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PurchaseItemService {
    PurchaseItem savePurchaseItem(PurchaseItem purchaseItem);
    Page<PurchaseItem> getAllPurchaseItems(Pageable pageable);
    PurchaseItem getPurchaseItemById(Long purchaseItemId);
    ResponseEntity deletePurchaseItem(Long purchaseItemId);
    PurchaseItem updatePurchaseItem(PurchaseItem purchaseItem);
}