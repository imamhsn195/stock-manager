package me.imamhasan.stockmanager.repository;

import me.imamhasan.stockmanager.model.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testSaveAddress() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");

        addressRepository.save(address);

        assertNotNull(address.getId());
        assertEquals(1, addressRepository.count());
    }

    @Test
    public void testFindAddressById() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");

        addressRepository.save(address);

        Address foundAddress = addressRepository.findById(address.getId()).orElse(null);

        assertNotNull(foundAddress);
        assertEquals("123 Main St", foundAddress.getStreet());
        assertEquals("Anytown", foundAddress.getCity());
        assertEquals("CA", foundAddress.getState());
        assertEquals("USA", foundAddress.getCountry());
        assertEquals("12345", foundAddress.getZipCode());
    }
}