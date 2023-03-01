package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Purchase;
import me.imamhasan.stockmanager.model.*;
import me.imamhasan.stockmanager.repository.PurchaseItemRepository;
import me.imamhasan.stockmanager.repository.PurchaseRepository;
import me.imamhasan.stockmanager.repository.ProductCategoryRepository;
import me.imamhasan.stockmanager.repository.ProductRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PurchaseItemServiceImplTest {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    PurchaseItemRepository purchaseItemRepository;
    @Autowired
    AddressServiceImpl addressService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    PurchaseServiceImpl purchaseService;
    @Autowired
    PurchaseItemServiceImpl purchaseItemService;

    @BeforeAll
    public static void setUpBeforeAll() {

    }

    @BeforeEach
    public void beforeEachTest() {}

    @Test
    void savePurchaseItem() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPrice(BigDecimal.valueOf(5200));
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
        purchaseItem = purchaseItemService.savePurchaseItem(purchaseItem);
        assertNotNull(purchaseItem.getId());
    }

    @Test
    void getAllPurchaseItems() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPrice(BigDecimal.valueOf(5200));
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
    void getPurchaseItemById() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPrice(BigDecimal.valueOf(5200));
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
    void deletePurchaseItem() throws IllegalStateException{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPrice(BigDecimal.valueOf(5200));
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

    @Test
    void updatePurchaseItem() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPrice(BigDecimal.valueOf(5200));
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

    @AfterEach
    public void clearDatabase() {
        purchaseItemRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE purchase_items ALTER COLUMN id RESTART WITH 1");
    }

    @AfterAll
    public static void setUpAfterAll(){}
}