package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.*;
import me.imamhasan.stockmanager.repository.ProductCategoryRepository;
import me.imamhasan.stockmanager.repository.ProductRepository;
import me.imamhasan.stockmanager.repository.PurchaseItemRepository;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static config.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName(PURCHASE_ITEM_TESTING_CLASS_DISPLAY_NAME)
class PurchaseItemServiceImplTest {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PurchaseItemRepository purchaseItemRepository;
    @Autowired
    AddressServiceImpl addressService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    PurchaseServiceImpl purchaseService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    PurchaseItemServiceImpl purchaseItemService;

    @BeforeAll
    public static void setUpBeforeAll() {}

    @BeforeEach
    public void beforeEachTest() {}

    @Test
    @Order(SAVE_RECORD_TESTING_ORDER)
    @DisplayName(SAVE_RECORD_TESTING_DISPLAY_NAME)
    void savePurchaseItem() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(1500));
        product.setSalePrice(BigDecimal.valueOf(2000));
        product.setProductCategory(productCategory);
        product.setQuantity(5);
        product = productRepository.save(product);
        assertEquals(BigDecimal.valueOf(1500), product.getPurchasePrice());
        assertEquals(BigDecimal.valueOf(2000), product.getSalePrice());
        assertNotNull(product.getId());

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
        supplier.setAddress(address);
        supplier.setPhone("(123)456-7890");
        supplier = supplierService.saveSupplier(supplier);
        assertNotNull(supplier.getId());

        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(LocalDate.now());
        purchase = purchaseService.savePurchase(purchase);
        assertNotNull(purchase.getId());

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setPurchase(purchase);
        product.setPurchasePrice(BigDecimal.valueOf(1800));
        product.setSalePrice(BigDecimal.valueOf(2100));
        purchaseItem.setProduct(product);
        purchaseItem.setQuantityPurchased(10);
        purchaseItem = purchaseItemService.savePurchaseItem(purchaseItem);
        assertNotNull(purchaseItem.getId());
        Product newProduct = productService.getProductById(1L);
        assertEquals(BigDecimal.valueOf(1800), newProduct.getPurchasePrice());
        assertEquals(BigDecimal.valueOf(2100), newProduct.getSalePrice());
    }

    @Test
    @Order(GET_SAVED_RECORDS_TESTING_ORDER)
    @DisplayName(GET_RECORD_TESTING_DISPLAY_NAME)
    void getAllPurchaseItems() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(300));
        product.setSalePrice(BigDecimal.valueOf(400));
        product.setProductCategory(productCategory);
        product.setQuantity(5);
        product = productRepository.save(product);
        assertNotNull(product.getId());

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
        supplier.setAddress(address);
        supplier.setPhone("(123)456-7890");
        supplier = supplierService.saveSupplier(supplier);
        assertNotNull(supplier.getId());

        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(LocalDate.now());
        purchase = purchaseService.savePurchase(purchase);
        assertNotNull(purchase.getId());

        PurchaseItem purchaseItem1 = new PurchaseItem();
        purchaseItem1.setPurchase(purchase);
        purchaseItem1.setProduct(product);
        purchaseItem1.setQuantityPurchased(10);

        PurchaseItem purchaseItem2 = new PurchaseItem();
        purchaseItem2.setPurchase(purchase);
        purchaseItem2.setProduct(product);
        purchaseItem2.setQuantityPurchased(10);

        List<PurchaseItem> purchaseItems = new ArrayList<>();
        purchaseItems.add(purchaseItem1);
        purchaseItems.add(purchaseItem2);

        purchaseItemRepository.saveAll(purchaseItems);

        Page<PurchaseItem> purchaseItemsSaved = purchaseItemService.getAllPurchaseItems(Pageable.ofSize(2));

        assertEquals(2, purchaseItemsSaved.getTotalElements());
    }

    @Test
    @Order(GET_RECORD_BY_ID_TESTING_ORDER)
    @DisplayName(GET_RECORD_BY_ID_TESTING_DISPLAY_NAME)
    void getPurchaseItemById() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(450));
        product.setSalePrice(BigDecimal.valueOf(500));
        product.setProductCategory(productCategory);
        product.setQuantity(5);
        product = productRepository.save(product);
        assertNotNull(product.getId());

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
        supplier.setAddress(address);
        supplier.setPhone("(123)456-7890");
        supplier = supplierService.saveSupplier(supplier);
        assertNotNull(supplier.getId());

        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(LocalDate.now());
        purchase = purchaseService.savePurchase(purchase);
        assertNotNull(purchase.getId());

        PurchaseItem purchaseItem1 = new PurchaseItem();
        purchaseItem1.setPurchase(purchase);
        purchaseItem1.setProduct(product);
        purchaseItem1.setQuantityPurchased(10);

        PurchaseItem purchaseItem2 = new PurchaseItem();
        purchaseItem2.setPurchase(purchase);
        purchaseItem2.setProduct(product);
        purchaseItem2.setQuantityPurchased(10);

        List<PurchaseItem> purchaseItems = new ArrayList<>();
        purchaseItems.add(purchaseItem1);
        purchaseItems.add(purchaseItem2);

        purchaseItemRepository.saveAll(purchaseItems);

        PurchaseItem purchaseItem = purchaseItemService.getPurchaseItemById(1L);

        assertNotNull(purchaseItem);
    }
    @Test
    @Order(UPDATE_RECORD_TESTING_ORDER)
    @DisplayName(UPDATE_RECORD_TESTING_DISPLAY_NAME)
    void updatePurchaseItem() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(1020));
        product.setSalePrice(BigDecimal.valueOf(1100));
        product.setProductCategory(productCategory);
        product.setQuantity(5);
        product = productRepository.save(product);
        assertNotNull(product.getId());

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
        supplier.setAddress(address);
        supplier.setPhone("(123)456-7890");
        supplier = supplierService.saveSupplier(supplier);
        assertNotNull(supplier.getId());

        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(LocalDate.now());
        purchase = purchaseService.savePurchase(purchase);
        assertNotNull(purchase.getId());

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setPurchase(purchase);
        purchaseItem.setProduct(product);
        purchaseItem.setQuantityPurchased(10);
        purchaseItemRepository.save(purchaseItem);
        assertNotNull(purchaseItem.getId());

        PurchaseItem purchaseItemSaved = purchaseItemService.getPurchaseItemById(1L);
        assertEquals(10, purchaseItemSaved.getQuantityPurchased());

        purchaseItemSaved.setQuantityPurchased(15);
        purchaseItemService.updatePurchaseItem(purchaseItemSaved);

        PurchaseItem purchaseItemUpdated = purchaseItemService.getPurchaseItemById(1L);

        assertEquals(15, purchaseItemSaved.getQuantityPurchased());

    }

    @Test
    @Order(DELETE_RECORD_TESTING_ORDER)
    @DisplayName(DELETE_RECORD_TESTING_DISPLAY_NAME)
    void deletePurchaseItem() throws IllegalStateException{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(850));
        product.setSalePrice(BigDecimal.valueOf(900));
        product.setProductCategory(productCategory);
        product.setQuantity(5);
        product = productRepository.save(product);
        assertNotNull(product.getId());

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
        supplier.setAddress(address);
        supplier.setPhone("(123)456-7890");
        supplier = supplierService.saveSupplier(supplier);
        assertNotNull(supplier.getId());

        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(LocalDate.now());
        purchase = purchaseService.savePurchase(purchase);
        assertNotNull(purchase.getId());

        PurchaseItem purchaseItem1 = new PurchaseItem();
        purchaseItem1.setPurchase(purchase);
        purchaseItem1.setProduct(product);
        purchaseItem1.setQuantityPurchased(10);

        PurchaseItem purchaseItem2 = new PurchaseItem();
        purchaseItem2.setPurchase(purchase);
        purchaseItem2.setProduct(product);
        purchaseItem2.setQuantityPurchased(10);

        List<PurchaseItem> purchaseItems = new ArrayList<>();
        purchaseItems.add(purchaseItem1);
        purchaseItems.add(purchaseItem2);

        purchaseItemRepository.saveAll(purchaseItems);
        purchaseItemService.deletePurchaseItem(1L);
        assertThrows(IllegalStateException.class, ()->{
            purchaseItemService.getPurchaseItemById(1L);
        });
    }

    @AfterEach
    public void clearDatabase() {
        purchaseItemRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE purchase_items ALTER COLUMN id RESTART WITH 1");
    }

    @AfterAll
    public static void setUpAfterAll(){}
}