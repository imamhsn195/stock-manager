package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Supplier;
import me.imamhasan.stockmanager.repository.AddressRepository;
import me.imamhasan.stockmanager.repository.SupplierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@AllArgsConstructor
@Transactional
@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final AddressRepository addressRepository;

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        if(supplier.getAddress().getId() != null){
            Long addressId = supplier.getAddress().getId();
            addressRepository.findById(addressId).orElseThrow(() -> new IllegalStateException("Address not found with id " + addressId));
        }else{
            Address address = addressRepository.save(supplier.getAddress());
            supplier.setAddress(address);
        }
        return supplierRepository.save(supplier);
    }

    @Override
    public Page<Supplier> getAllSuppliers(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }

    @Override
    public Supplier getSupplierById(Long supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalStateException("Supplier with id:  ${supplierId} not found"));
    }

    @Override
    public ResponseEntity deleteSupplier(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalStateException("Supplier with id:  ${supplierId} not found"));
        supplierRepository.delete(supplier);
        return ResponseEntity.ok().body("Supplier deleted successfully.");
    }

    @Override
    public Supplier updateSupplier(Supplier supplier) {
        supplierRepository.findById(supplier.getId())
                .orElseThrow(() -> new IllegalStateException("Supplier with id " + supplier.getId() + " not found"));
        if(supplier.getAddress().getId() != null){
            Long addressId = supplier.getAddress().getId();
            addressRepository.findById(addressId).orElseThrow(() -> new IllegalStateException("Address not found with id " + addressId));
        }else{
            Address address = addressRepository.save(supplier.getAddress());
            supplier.setAddress(address);
        }
        return supplierRepository.save(supplier);
    }
}