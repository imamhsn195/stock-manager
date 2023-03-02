package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.Product;
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
    public SaleItem saveSaleItem(@NotNull SaleItem saleItem) {
        //  validate Sale id
        if(saleItem.getSale().getId() != null){
            Long SaleId = saleItem.getSale().getId();
            saleRepository.findById(SaleId).orElseThrow(() -> new IllegalStateException("Sale not found with id " + SaleId));
        }
        //  validate product id
        if(saleItem.getProduct().getId() != null){
            Long productId = saleItem.getProduct().getId();
            Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product not found with id " + productId));
            if(product.getQuantity() < saleItem.getQuantitySold()){throw new IllegalStateException("Available product quantity " + product.getQuantity() + " is less than quantity sold "+ saleItem.getQuantitySold() +".");}
            Integer qty = product.getQuantity() - saleItem.getQuantitySold();
            product.setQuantity(qty);
            productRepository.save(product);
        }
        return saleItemRepository.save(saleItem);
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
    public SaleItem updateSaleItem(@NotNull SaleItem saleItem) {
        //  validate Sale id
        if(saleItem.getSale().getId() != null){
            Long SaleId = saleItem.getSale().getId();
            saleRepository.findById(SaleId).orElseThrow(() -> new IllegalStateException("Sale not found with id " + SaleId));
        }
        //  validate product id
        if(saleItem.getProduct().getId() != null){
            Long productId = saleItem.getProduct().getId();
            Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product not found with id " + productId));
            Integer previousQty = saleItemRepository.findById(saleItem.getId()).get().getQuantitySold();
            if((product.getQuantity() + previousQty) < saleItem.getQuantitySold()){ throw new IllegalStateException("Available product quantity " + product.getQuantity() + previousQty +" is less than quantity sold "+ saleItem.getQuantitySold() +".");}
            Integer qty = product.getQuantity() - saleItem.getQuantitySold() + previousQty;
            product.setQuantity(qty);

            productRepository.save(product);
        }
        return saleItemRepository.save(saleItem);
    }
}
