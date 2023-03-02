package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Customer;
import me.imamhasan.stockmanager.model.Sale;
import me.imamhasan.stockmanager.repository.AddressRepository;
import me.imamhasan.stockmanager.repository.CustomerRepository;
import me.imamhasan.stockmanager.repository.SaleRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;


    @Override
    public Sale saveSale(@NotNull Sale sale) {
//        save the sale
        if(sale.getCustomer().getId() != null){
            Long customerId = sale.getCustomer().getId();
            customerRepository.findById(customerId).orElseThrow(() -> new IllegalStateException("Customer not found with id " + customerId));
        }else{
            if (sale.getCustomer().getAddress().getId() != null){
                addressRepository.findById(sale.getCustomer().getAddress().getId())
                        .orElseThrow(() -> new IllegalStateException("Address not found with id " + sale.getCustomer().getAddress().getId()));
            }else{
                Address address = addressRepository.save(sale.getCustomer().getAddress());
                sale.getCustomer().setAddress(address);
            }
            Customer customer = customerRepository.save(sale.getCustomer());
            sale.setCustomer(customer);
        }
        return saleRepository.save(sale);
    }

    @Override
    public Page<Sale> getAllSales(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }

    @Override
    public Sale getSaleById(Long saleId) {
        return saleRepository.findById(saleId)
                .orElseThrow(() -> new IllegalStateException("Sale with id " + saleId + " not found"));
    }

    @Override
    public ResponseEntity deleteSale(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new IllegalStateException("Sale with id " + saleId + " not found"));
        saleRepository.delete(sale);
        return ResponseEntity.ok().body("Sale deleted successfully.");
    }

    @Override
    public Sale updateSale(@NotNull Sale sale) {
//        find the sale
        saleRepository.findById(sale.getId())
                .orElseThrow(() -> new IllegalStateException("Sale with id " + sale.getId() + " not found"));
//        save the sale
        if(sale.getCustomer().getId() != null){
            Long customerId = sale.getCustomer().getId();
            customerRepository.findById(customerId).orElseThrow(() -> new IllegalStateException("Customer not found with id " + customerId));
        }else{
            if (sale.getCustomer().getAddress().getId() != null){
                addressRepository.findById(sale.getCustomer().getAddress().getId())
                        .orElseThrow(() -> new IllegalStateException("Address not found with id " + sale.getCustomer().getAddress().getId()));
            }else{
                Address address = addressRepository.save(sale.getCustomer().getAddress());
                sale.getCustomer().setAddress(address);
            }
            Customer customer = customerRepository.save(sale.getCustomer());
            sale.setCustomer(customer);
        }
        return saleRepository.save(sale);
    }
}
