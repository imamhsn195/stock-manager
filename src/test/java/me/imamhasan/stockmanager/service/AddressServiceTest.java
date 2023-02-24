package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.repository.AddressRepository;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AddressServiceTest {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressServiceImpl addressService;
    @BeforeAll
    public void clearProductTable() {}
    @BeforeEach
    private void beforeEachTest() {}
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

        Page<Address> savedAddresses = addressService.getAllAddresses(Pageable.ofSize(2));

        assertNotNull(savedAddresses);

        assertEquals(addresses.size(), savedAddresses.getTotalElements());

        Address savedAddress1 = savedAddresses.getContent().get(0);
        assertEquals("123 Main St", savedAddress1.getStreet());
        assertEquals("New York", savedAddress1.getCity());
        assertEquals("NY", savedAddress1.getState());
        assertEquals("USA", savedAddress1.getCountry());
        assertEquals("10001", savedAddress1.getZipCode());

        Address savedAddress2 = savedAddresses.getContent().get(1);
        assertEquals("456 Maple Ave", savedAddress2.getStreet());
        assertEquals("San Francisco", savedAddress2.getCity());
        assertEquals("CA", savedAddress2.getState());
        assertEquals("USA", savedAddress2.getCountry());
        assertEquals("94102", savedAddress2.getZipCode());
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
    @AfterEach
    public void clearDatabase() {
        addressRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE address ALTER COLUMN id RESTART WITH 1");
    }

    @AfterAll
    private void setUpAfterAll(){}
}
