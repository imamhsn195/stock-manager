package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Page<Customer> getAllCustomers(Pageable pageable);
    Customer getCustomerById(Long customerId);
    void deleteCustomer(Long customerId);
    Customer updateCustomer(Customer customer);
}