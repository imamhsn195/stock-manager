package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Supplier;
import me.imamhasan.stockmanager.model.Purchase;
import me.imamhasan.stockmanager.repository.AddressRepository;
import me.imamhasan.stockmanager.repository.SupplierRepository;
import me.imamhasan.stockmanager.repository.PurchaseRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PurchaseServiceImpl implements PurchaseService{
    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository customerRepository;
    private final AddressRepository addressRepository;

    @Override
    public Purchase savePurchase(@NotNull Purchase purchase) {
//        save the purchase
        if(purchase.getSupplier().getId() != null){
            Long customerId = purchase.getSupplier().getId();
            customerRepository.findById(customerId).orElseThrow(() -> new IllegalStateException("Supplier not found with id " + customerId));
        }else{
            if (purchase.getSupplier().getAddress().getId() != null){
                addressRepository.findById(purchase.getSupplier().getAddress().getId())
                        .orElseThrow(() -> new IllegalStateException("Address not found with id " + purchase.getSupplier().getAddress().getId()));
            }else{
                Address address = addressRepository.save(purchase.getSupplier().getAddress());
                purchase.getSupplier().setAddress(address);
            }
            Supplier customer = customerRepository.save(purchase.getSupplier());
            purchase.setSupplier(customer);
        }
        return purchaseRepository.save(purchase);
    }

    @Override
    public Page<Purchase> getAllPurchases(Pageable pageable) {
        return purchaseRepository.findAll(pageable);
    }

    @Override
    public Purchase getPurchaseById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalStateException("Purchase with id " + purchaseId + " not found"));
    }

    @Override
    public ResponseEntity deletePurchase(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalStateException("Purchase with id " + purchaseId + " not found"));
        purchaseRepository.delete(purchase);
        return ResponseEntity.ok().body("Purchase deleted successfully.");
    }

    @Override
    public Purchase updatePurchase(@NotNull Purchase purchase) {
//        find the purchase
        purchaseRepository.findById(purchase.getId())
                .orElseThrow(() -> new IllegalStateException("Purchase with id " + purchase.getId() + " not found"));
//        save the purchase
        if(purchase.getSupplier().getId() != null){
            Long customerId = purchase.getSupplier().getId();
            customerRepository.findById(customerId).orElseThrow(() -> new IllegalStateException("Supplier not found with id " + customerId));
        }else{
            if (purchase.getSupplier().getAddress().getId() != null){
                addressRepository.findById(purchase.getSupplier().getAddress().getId())
                        .orElseThrow(() -> new IllegalStateException("Address not found with id " + purchase.getSupplier().getAddress().getId()));
            }else{
                Address address = addressRepository.save(purchase.getSupplier().getAddress());
                purchase.getSupplier().setAddress(address);
            }
            Supplier customer = customerRepository.save(purchase.getSupplier());
            purchase.setSupplier(customer);
        }
        return purchaseRepository.save(purchase);
    }
}
