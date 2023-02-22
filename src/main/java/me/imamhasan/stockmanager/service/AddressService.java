package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Address;

import java.util.List;


public interface AddressService {
    Address saveAddress(Address address);
    List<Address> getAllAddresses();
    Address getAddressById(Long addressId);
    void deleteAddress(Long addressId);
    Address updateAddress(Address address);
}