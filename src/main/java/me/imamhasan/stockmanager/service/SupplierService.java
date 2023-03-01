package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface SupplierService {
    Supplier saveSupplier(Supplier supplier);
    Page<Supplier> getAllSuppliers(Pageable pageable);
    Supplier getSupplierById(Long supplierId);
    ResponseEntity deleteSupplier(Long supplierId);
    Supplier updateSupplier(Supplier supplier);
}