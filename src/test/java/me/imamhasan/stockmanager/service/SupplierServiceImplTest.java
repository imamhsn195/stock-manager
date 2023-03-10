package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Supplier;
import me.imamhasan.stockmanager.repository.SupplierRepository;
import org.junit.jupiter.api.*;
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

import static config.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName(SUPPLIER_TESTING_CLASS_DISPLAY_NAME)
class SupplierServiceImplTest {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    SupplierServiceImpl supplierService;
    @Autowired
    AddressService addressService;

    @BeforeAll
    public static void setUpBeforeAll() {}
    @BeforeEach
    public void setUpBeforeEach() {}
    @Test
    @Order(SAVE_RECORD_TESTING_ORDER)
    @DisplayName(SAVE_RECORD_TESTING_DISPLAY_NAME)
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
    @Order(GET_SAVED_RECORDS_TESTING_ORDER)
    @DisplayName(GET_RECORD_TESTING_DISPLAY_NAME)
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
        supplierService.saveSupplier(supplier);

        Page<Supplier> suppliers = supplierService.getAllSuppliers(Pageable.ofSize(1));
        assertNotNull(suppliers);

        assertEquals(1, suppliers.getTotalElements());
    }

    @Test
    @Order(GET_RECORD_BY_ID_TESTING_ORDER)
    @DisplayName(GET_RECORD_BY_ID_TESTING_DISPLAY_NAME)
    void getSupplierById() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        addressService.saveAddress(address);

        Supplier supplier = new Supplier();
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");
        assertNotNull(address.getId());
        supplier.setAddress(address);
        supplierService.saveSupplier(supplier);

        Supplier supplierSaved = supplierService.getSupplierById(1L);
        assertNotNull(supplierSaved);
    }
    @Test
    @Order(UPDATE_RECORD_TESTING_ORDER)
    @DisplayName(UPDATE_RECORD_TESTING_DISPLAY_NAME)
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
    @Order(DELETE_RECORD_TESTING_ORDER)
    @DisplayName(DELETE_RECORD_TESTING_DISPLAY_NAME)
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
    public void setUpAfterEach() {
        supplierRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE suppliers ALTER COLUMN id RESTART WITH 1");
    }

    @AfterAll
    public static void setUpAfterAll(){}
}