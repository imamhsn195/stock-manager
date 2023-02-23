package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AddressService {
    Address saveAddress(Address address);
    Page<Address> getAllAddresses(Pageable pageable);
    Address getAddressById(Long addressId);
    void deleteAddress(Long addressId);
    Address updateAddress(Address address);
}