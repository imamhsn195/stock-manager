package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Supplier;
import me.imamhasan.stockmanager.repository.AddressRepository;
import me.imamhasan.stockmanager.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
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
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getSupplierById(Long supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalStateException("Supplier with id:  ${supplierId} not found"));
    }

    @Override
    public void deleteSupplier(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalStateException("Supplier with id:  ${supplierId} not found"));
        supplierRepository.delete(supplier);
    }

    @Override
    public Supplier updateSupplier(Supplier supplier) {
        supplierRepository.findById(supplier.getId())
                .orElseThrow(() -> new IllegalStateException("Supplier with id " + supplier.getId() + " not found"));
        return supplierRepository.save(supplier);
    }
}