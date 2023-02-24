package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Supplier;
import me.imamhasan.stockmanager.repository.SupplierRepository;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SupplierServiceImplTest {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private SupplierServiceImpl supplierService;

    @Autowired
    private AddressService addressService;

    @BeforeAll
    public void clearProductTable() {}
    @BeforeEach
    private void beforeEachTest() {}
    @Test
    public void testCreateSupplier() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        addressService.saveAddress(address);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");
        assertNotNull(address.getId());
        supplier.setAddress(address);

        Supplier savedSupplier = supplierService.saveSupplier(supplier);
        assertNotNull(savedSupplier);
        assertEquals("John Doe", savedSupplier.getName());
        assertEquals("johndoe@example.com", savedSupplier.getEmail());
        assertEquals("(123)456-7890", savedSupplier.getPhone());
        assertEquals(address.getId(), savedSupplier.getAddress().getId());
    }

    @Test
    public void testFindAllSuppliers() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        addressService.saveAddress(address);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");
        assertNotNull(address.getId());
        supplier.setAddress(address);
        Supplier savedSupplier = supplierService.saveSupplier(supplier);

        List<Supplier> suppliers = supplierService.getAllSuppliers();
        assertNotNull(suppliers);
        assertTrue(suppliers.size() > 0);
    }

    @Test
    public void testUpdateSupplier() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        addressService.saveAddress(address);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");
        assertNotNull(address.getId());
        supplier.setAddress(address);
        Supplier savedSupplier = supplierService.saveSupplier(supplier);
        assertNotNull(savedSupplier);

        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setId(savedSupplier.getId());
        updatedSupplier.setName("Jane Smith");
        updatedSupplier.setEmail("janesmith@example.com");
        updatedSupplier.setPhone("(987)654-3210");
        updatedSupplier.setAddress(address);

        Supplier result = supplierService.updateSupplier(updatedSupplier);

        assertNotNull(result);
        assertEquals("Jane Smith", result.getName());
        assertEquals("janesmith@example.com", result.getEmail());
        assertEquals("(987)654-3210", result.getPhone());
        assertEquals(address.getId(), result.getAddress().getId());
    }

    @Test
    public void testDeleteSupplier() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        addressService.saveAddress(address);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");
        assertNotNull(address.getId());
        supplier.setAddress(address);
        Supplier savedSupplier = supplierService.saveSupplier(supplier);
        supplierService.deleteSupplier(savedSupplier.getId());
        assertThrows(IllegalStateException.class, ()->{
            supplierService.getSupplierById(savedSupplier.getId());
        });
    }
    @AfterEach
    public void clearDatabase() {
        supplierRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE supplier ALTER COLUMN id RESTART WITH 1");
    }

    @AfterAll
    private void setUpAfterAll(){}
}