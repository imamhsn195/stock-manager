package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Purchase;
import me.imamhasan.stockmanager.model.Supplier;
import me.imamhasan.stockmanager.repository.PurchaseRepository;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static config.TestConstants.*;
import static org.junit.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName(PURCHASE_TESTING_CLASS_DISPLAY_NAME)
class PurchaseServiceImplTest {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    SupplierService supplierService;
    @Autowired
    PurchaseServiceImpl purchaseService;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    AddressServiceImpl addressService;

    @BeforeAll
    public static void clearProductTable() {}
    @BeforeEach
    public void beforeEachTest() {}
    @Order(SAVE_RECORD_TESTING_ORDER)
    @DisplayName(SAVE_RECORD_TESTING_DISPLAY_NAME)
    @Test()
    void savePurchase() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        address = addressService.saveAddress(address);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");

        Assertions.assertNotNull(address.getId());

        supplier.setAddress(address);

        // save the supplier
        supplier = supplierService.saveSupplier(supplier);

        // create a purchase with the supplier ID
        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(LocalDate.now());

        // save the purchase
        purchase = purchaseService.savePurchase(purchase);

        // assert that the purchase was saved successfully
        assertNotNull(purchase.getId());
    }

    @Test
    @Order(GET_SAVED_RECORDS_TESTING_ORDER)
    @DisplayName(GET_RECORD_TESTING_DISPLAY_NAME)
    void getAllPurchases() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        address = addressService.saveAddress(address);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");

        Assertions.assertNotNull(address.getId());

        supplier.setAddress(address);

        // save the supplier
        supplier = supplierService.saveSupplier(supplier);

        // create a purchase with the supplier ID
        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(LocalDate.now());

        Purchase purchase1 = new Purchase();
        purchase1.setSupplier(supplier);
        purchase1.setPurchaseDate(LocalDate.now());

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase);
        purchases.add(purchase1);
        purchaseRepository.saveAll(purchases);
        Page<Purchase> savedPurchases = purchaseService.getAllPurchases(Pageable.ofSize(2));
        assertEquals(2, savedPurchases.getTotalElements());
    }

    @Test
    @Order(GET_RECORD_BY_ID_TESTING_ORDER)
    @DisplayName(GET_RECORD_BY_ID_TESTING_DISPLAY_NAME)
    void getPurchaseById() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        address = addressService.saveAddress(address);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");

        Assertions.assertNotNull(address.getId());

        supplier.setAddress(address);

        // save the supplier
        supplier = supplierService.saveSupplier(supplier);

        // create a purchase with the supplier ID
        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(LocalDate.now());

        // save the purchase
        purchase = purchaseRepository.save(purchase);

        assertNotNull(purchaseService.getPurchaseById(purchase.getId()));
    }
    @Test
    @Order(UPDATE_RECORD_TESTING_ORDER)
    @DisplayName(UPDATE_RECORD_TESTING_DISPLAY_NAME)
    void updatePurchase() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        address = addressService.saveAddress(address);
        assertNotNull(address.getId());

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");

        Assertions.assertNotNull(address.getId());

        supplier.setAddress(address);

        // save the supplier
        supplier = supplierService.saveSupplier(supplier);
        assertNotNull(supplier.getId());

        // create a purchase with the supplier ID
        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(LocalDate.parse("2023-01-20"));

        // save the purchase
        purchase = purchaseService.savePurchase(purchase);

        // assert that the purchase was saved successfully
        assertNotNull(purchase.getId());

        assertEquals("John Doe", purchase.getSupplier().getName());

        assertEquals("123 Main St", purchase.getSupplier().getAddress().getStreet());

        purchase.setPurchaseDate(purchase.getPurchaseDate().plusDays(2));

        Purchase purchaseUpdated = purchaseService.updatePurchase(purchase);

        assertEquals(LocalDate.parse("2023-01-22"), purchaseUpdated.getPurchaseDate());

    }

    @Test
    @Order(DELETE_RECORD_TESTING_ORDER)
    @DisplayName(DELETE_RECORD_TESTING_DISPLAY_NAME)
    void deletePurchase() throws IllegalStateException{
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        address = addressService.saveAddress(address);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");
        supplier.setEmail("johndoe@example.com");
        supplier.setPhone("(123)456-7890");

        Assertions.assertNotNull(address.getId());

        supplier.setAddress(address);

        // save the supplier
        supplier = supplierService.saveSupplier(supplier);

        // create an purchase with the supplier ID
        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(LocalDate.now());

        // save the purchase
        purchase = purchaseService.savePurchase(purchase);

        // assert that the purchase was saved successfully
        assertNotNull(purchase.getId());

        Long id = purchase.getId();
        purchaseService.deletePurchase(purchase.getId());

        assertThrows(IllegalStateException.class, ()->{
            purchaseService.getPurchaseById(id);
        });

    }

    @AfterEach
    public void clearDatabase() {
        purchaseRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE purchases ALTER COLUMN id RESTART WITH 1");
    }
    @AfterAll
    public static void setUpAfterAll(){}
}