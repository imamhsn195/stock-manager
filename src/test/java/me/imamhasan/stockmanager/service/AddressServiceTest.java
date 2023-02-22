package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.repository.AddressRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AddressServiceTest {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressServiceImpl addressService;

    @Test
    public void testFindAllAddresses() {
        Address address1 = new Address();
        address1.setId(1L);
        address1.setStreet("123 Main St");
        address1.setCity("New York");
        address1.setState("NY");
        address1.setCountry("USA");
        address1.setZipCode("10001");

        Address address2 = new Address();
        address2.setId(2L);
        address2.setStreet("456 Maple Ave");
        address2.setCity("San Francisco");
        address2.setState("CA");
        address2.setCountry("USA");
        address2.setZipCode("94102");

        List<Address> addresses = new ArrayList<>();
        addresses.add(address1);
        addresses.add(address2);
        addressRepository.saveAll(addresses);

        List<Address> result = addressService.getAllAddresses();

        assertNotNull(result);
        assertEquals(2, result.size());

        Address result1 = result.get(0);
        assertEquals("123 Main St", result1.getStreet());
        assertEquals("New York", result1.getCity());
        assertEquals("NY", result1.getState());
        assertEquals("USA", result1.getCountry());
        assertEquals("10001", result1.getZipCode());

        Address result2 = result.get(1);
        assertEquals("456 Maple Ave", result2.getStreet());
        assertEquals("San Francisco", result2.getCity());
        assertEquals("CA", result2.getState());
        assertEquals("USA", result2.getCountry());
        assertEquals("94102", result2.getZipCode());
    }

    @Test
    public void testSaveAddress() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");

        Address savedAddress = addressService.saveAddress(address);

        assertNotNull(savedAddress.getId());
        assertEquals(address.getStreet(), savedAddress.getStreet());
        assertEquals(address.getCity(), savedAddress.getCity());
        assertEquals(address.getState(), savedAddress.getState());
        assertEquals(address.getCountry(), savedAddress.getCountry());
        assertEquals(address.getZipCode(), savedAddress.getZipCode());
    }

    @Test
    public void testGetAddressById() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");

        Address savedAddress = addressService.saveAddress(address);

        Address retrievedAddress = addressService.getAddressById(savedAddress.getId());

        assertNotNull(retrievedAddress);
        assertEquals(savedAddress.getId(), retrievedAddress.getId());
        assertEquals(address.getStreet(), retrievedAddress.getStreet());
        assertEquals(address.getCity(), retrievedAddress.getCity());
        assertEquals(address.getState(), retrievedAddress.getState());
        assertEquals(address.getCountry(), retrievedAddress.getCountry());
        assertEquals(address.getZipCode(), retrievedAddress.getZipCode());
    }

//    @Test
//    public void testUpdateAddress() {
//        Address address = new Address();
//        address.setStreet("123 Main St");
//        address.setCity("Anytown");
//        address.setState("CA");
//        address.setCountry("USA");
//        address.setZipCode("12345");
//
//        Address savedAddress = addressService.saveAddress(address);
//
//        Address updatedAddress = new Address();
//        updatedAddress.setId(savedAddress.getId());
//        updatedAddress.setStreet("456 Oak Ave");
//        updatedAddress.setCity("Othertown");
//        updatedAddress.setState("NY");
//        updatedAddress.setCountry("USA");
//        updatedAddress.setZipCode("67890");
//
//        Address savedUpdatedAddress = addressService.saveAddress(updatedAddress);
//
//        assertEquals(savedAddress.getId(), savedUpdatedAddress.getId());
//        assertEquals(updatedAddress.getStreet(), savedUpdatedAddress.getStreet());
//        assertEquals(updatedAddress.getCity(), savedUpdatedAddress.getCity());
//        assertEquals(updatedAddress.getState(), savedUpdatedAddress.getState());
//        assertEquals(updatedAddress.getCountry(), savedUpdatedAddress.getCountry());
//        assertEquals(updatedAddress.getZipCode(), savedUpdatedAddress.getZipCode());
//    }

    @Test
    public void testDeleteAddress() throws IllegalStateException{
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");

        Address savedAddress = addressService.saveAddress(address);

        addressService.deleteAddress(savedAddress.getId());

        assertThrows(IllegalStateException.class, () -> {
            Address deletedAddress = addressService.getAddressById(savedAddress.getId());
        });
    }

    @Test
    public void testUpdateAddress() {
        // Create a new address object
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");

        // Save the address to the database
        addressService.saveAddress(address);

        // Update the address object
        address.setStreet("456 Oak St");
        address.setCity("Othertown");
        address.setState("NY");
        address.setCountry("USA");
        address.setZipCode("67890");

        // Save the updated address to the database
        addressService.updateAddress(address);

        // Retrieve the address from the database
        Address updatedAddress = addressRepository.findById(address.getId()).orElse(null);

        // Check that the address was updated correctly
        assertNotNull(updatedAddress);
        assertEquals("456 Oak St", updatedAddress.getStreet());
        assertEquals("Othertown", updatedAddress.getCity());
        assertEquals("NY", updatedAddress.getState());
        assertEquals("USA", updatedAddress.getCountry());
        assertEquals("67890", updatedAddress.getZipCode());
    }
}
