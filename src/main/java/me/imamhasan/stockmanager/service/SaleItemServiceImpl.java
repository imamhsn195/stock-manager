package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.SaleItem;
import me.imamhasan.stockmanager.repository.SaleItemRepository;
import me.imamhasan.stockmanager.repository.SaleRepository;
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
public class SaleItemServiceImpl implements SaleItemService{
    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;

    @Override
    public SaleItem saveSaleItem(@NotNull SaleItem SaleItem) {
        //  validate Sale id
        if(SaleItem.getSale().getId() != null){
            Long SaleId = SaleItem.getSale().getId();
            saleRepository.findById(SaleId).orElseThrow(() -> new IllegalStateException("Sale not found with id " + SaleId));
        }
        //  validate product id
        if(SaleItem.getProduct().getId() != null){
            Long productId = SaleItem.getProduct().getId();
            productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product not found with id " + productId));
        }
        return saleItemRepository.save(SaleItem);
    }

    @Override
    public Page<SaleItem> getAllSaleItems(Pageable pageable) {
        return saleItemRepository.findAll(pageable);
    }

    @Override
    public SaleItem getSaleItemById(Long SaleItemId) {
        return saleItemRepository.findById(SaleItemId)
                .orElseThrow(() -> new IllegalStateException("Sale with id " + SaleItemId + " not found"));
    }

    @Override
    public ResponseEntity deleteSaleItem(Long SaleItemId) {
        SaleItem SaleItem = saleItemRepository.findById(SaleItemId)
                .orElseThrow(() -> new IllegalStateException("Sale item with id " + SaleItemId + " not found"));
        saleItemRepository.delete(SaleItem);
        return ResponseEntity.ok().body("Sale item deleted successfully.");
    }

    @Override
    public SaleItem updateSaleItem(@NotNull SaleItem SaleItem) {
        //  validate Sale id
        if(SaleItem.getSale().getId() != null){
            Long SaleId = SaleItem.getSale().getId();
            saleRepository.findById(SaleId).orElseThrow(() -> new IllegalStateException("Sale not found with id " + SaleId));
        }
        //  validate product id
        if(SaleItem.getProduct().getId() != null){
            Long productId = SaleItem.getProduct().getId();
            productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product not found with id " + productId));
        }
        return saleItemRepository.save(SaleItem);
    }
}
