package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.PurchaseItem;
import me.imamhasan.stockmanager.repository.PurchaseItemRepository;
import me.imamhasan.stockmanager.repository.PurchaseRepository;
import me.imamhasan.stockmanager.repository.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PurchaseItemServiceImpl implements PurchaseItemService{
    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductRepository productRepository;

    @Override
    public PurchaseItem savePurchaseItem(@NotNull PurchaseItem purchaseItem) {
        //  validate purchase id
        if(purchaseItem.getPurchase().getId() != null){
            Long purchaseId = purchaseItem.getPurchase().getId();
            purchaseRepository.findById(purchaseId).orElseThrow(() -> new IllegalStateException("Purchase not found with id " + purchaseId));
        }
        //  validate product id
        if(purchaseItem.getProduct().getId() != null){
            Long productId = purchaseItem.getProduct().getId();
            productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product not found with id " + productId));
        }
        return purchaseItemRepository.save(purchaseItem);
    }

    @Override
    public Page<PurchaseItem> getAllPurchaseItems(Pageable pageable) {
        return purchaseItemRepository.findAll(pageable);
    }

    @Override
    public PurchaseItem getPurchaseItemById(Long purchaseItemId) {
        return purchaseItemRepository.findById(purchaseItemId)
                .orElseThrow(() -> new IllegalStateException("Purchase with id " + purchaseItemId + " not found"));
    }

    @Override
    public ResponseEntity deletePurchaseItem(Long purchaseItemId) {
        PurchaseItem purchaseItem = purchaseItemRepository.findById(purchaseItemId)
                .orElseThrow(() -> new IllegalStateException("Purchase item with id " + purchaseItemId + " not found"));
        purchaseItemRepository.delete(purchaseItem);
        return ResponseEntity.ok().body("Purchase item deleted successfully.");
    }

    @Override
    public PurchaseItem updatePurchaseItem(@NotNull PurchaseItem purchaseItem) {
        //  validate purchase id
        if(purchaseItem.getPurchase().getId() != null){
            Long purchaseId = purchaseItem.getPurchase().getId();
            purchaseRepository.findById(purchaseId).orElseThrow(() -> new IllegalStateException("Purchase not found with id " + purchaseId));
        }
        //  validate product id
        if(purchaseItem.getProduct().getId() != null){
            Long productId = purchaseItem.getProduct().getId();
            productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product not found with id " + productId));
        }
        return purchaseItemRepository.save(purchaseItem);
    }
}
