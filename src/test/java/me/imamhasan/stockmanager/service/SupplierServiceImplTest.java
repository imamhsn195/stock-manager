package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Supplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SupplierServiceImplTest {

    @Autowired
    private SupplierServiceImpl supplierService;

    @Autowired
    private AddressService addressService;

    private Address savedAddress;

    @Before
    public void setUp() {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        savedAddress = addressService.saveAddress(address);
    }

    @Test
    public void testCreateSupplier() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");
        supplier.setAddress(savedAddress);

        Supplier savedSupplier = supplierService.saveSupplier(supplier);
        assertNotNull(savedSupplier);
        assertEquals("John Doe", savedSupplier.getName());
        assertEquals("johndoe@example.com", savedSupplier.getEmail());
        assertEquals("(123)456-7890", savedSupplier.getPhone());
        assertEquals(savedAddress.getId(), savedSupplier.getAddress().getId());
    }

    @Test
    public void testFindAllSuppliers() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");
        supplier.setAddress(savedAddress);
        Supplier savedSupplier = supplierService.saveSupplier(supplier);

        List<Supplier> suppliers = supplierService.getAllSuppliers();
        assertNotNull(suppliers);
        assertTrue(suppliers.size() > 0);
    }

    @Test
    public void testUpdateSupplier() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");
        supplier.setAddress(savedAddress);
        Supplier savedSupplier = supplierService.saveSupplier(supplier);
        assertNotNull(savedSupplier);

        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setId(savedSupplier.getId());
        updatedSupplier.setName("Jane Smith");
        updatedSupplier.setEmail("janesmith@example.com");
        updatedSupplier.setPhone("(987)654-3210");
        updatedSupplier.setAddress(savedAddress);

        Supplier result = supplierService.updateSupplier(updatedSupplier);

        assertNotNull(result);
        assertEquals("Jane Smith", result.getName());
        assertEquals("janesmith@example.com", result.getEmail());
        assertEquals("(987)654-3210", result.getPhone());
        assertEquals(savedAddress.getId(), result.getAddress().getId());
    }

    @Test
    public void testDeleteSupplier() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");
        supplier.setAddress(savedAddress);
        Supplier savedSupplier = supplierService.saveSupplier(supplier);
        supplierService.deleteSupplier(savedSupplier.getId());
        assertThrows(IllegalStateException.class, ()->{
            supplierService.getSupplierById(savedSupplier.getId());
        });
    }

}