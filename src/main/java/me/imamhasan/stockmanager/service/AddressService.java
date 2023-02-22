package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Product;
import me.imamhasan.stockmanager.model.Supplier;

import java.util.List;


public interface SupplierService {
    Supplier saveSupplier(Supplier supplier);
    List<Supplier> getAllSuppliers();
    Supplier getSupplierById(Long supplierId);
    void deleteSupplier(Long supplierId);
    Supplier updateSupplier(Long id, Supplier supplier);
}