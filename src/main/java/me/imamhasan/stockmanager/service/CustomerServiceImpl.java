package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Customer;
import me.imamhasan.stockmanager.repository.AddressRepository;
import me.imamhasan.stockmanager.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        if(customer.getAddress().getId() != null){
            Long addressId = customer.getAddress().getId();
            addressRepository.findById(addressId).orElseThrow(() -> new IllegalStateException("Address not found with id " + addressId));
        }else{
            Address address = addressRepository.save(customer.getAddress());
            customer.setAddress(address);
        }
        return customerRepository.save(customer);
    }

    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("Customer with id:  ${customerId} not found"));
    }

    @Override
    public ResponseEntity<?> deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("Customer with id:  ${customerId} not found"));
        customerRepository.delete(customer);
        return ResponseEntity.ok().body("Customer deleted successfully.");
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        customerRepository.findById(customer.getId())
                .orElseThrow(() -> new IllegalStateException("Customer with id " + customer.getId() + " not found"));
        return customerRepository.save(customer);
    }
}