package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.*;
import me.imamhasan.stockmanager.repository.*;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName(SALE_ITEM_TESTING_CLASS_DISPLAY_NAME)
class SaleItemServiceImplTest {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SaleItemRepository saleItemRepository;
    @Autowired
    AddressServiceImpl addressService;
    @Autowired
    CustomerService customerService;
    @Autowired
    SaleServiceImpl saleService;
    @Autowired
    SaleItemServiceImpl saleItemService;

    @BeforeAll
    public static void setUpBeforeAll() {}

    @BeforeEach
    public void beforeEachTest() {}

    @Test
    @Order(SAVE_RECORD_TESTING_ORDER)
    @DisplayName(SAVE_RECORD_TESTING_DISPLAY_NAME)
    void saveSaleItem() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(5200));
        product.setSalePrice(BigDecimal.valueOf(5500));
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

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setAddress(address);
        customer.setPhone("(123)456-7890");
        customer = customerService.saveCustomer(customer);
        assertNotNull(customer.getId());

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDate.now());
        sale = saleService.saveSale(sale);
        assertNotNull(sale.getId());

        SaleItem saleItem = new SaleItem();
        saleItem.setSale(sale);
        saleItem.setPriceSold(BigDecimal.valueOf(152));
        saleItem.setProduct(product);
        saleItem.setQuantitySold(4);
        saleItem = saleItemService.saveSaleItem(saleItem);
        assertNotNull(saleItem.getId());
    }

    @Test
    @Order(GET_SAVED_RECORDS_TESTING_ORDER)
    @DisplayName(GET_RECORD_TESTING_DISPLAY_NAME)
    void getAllSaleItems() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(5200));
        product.setSalePrice(BigDecimal.valueOf(5500));
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

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setAddress(address);
        customer.setPhone("(123)456-7890");
        customer = customerService.saveCustomer(customer);
        assertNotNull(customer.getId());

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDate.now());
        sale = saleService.saveSale(sale);
        assertNotNull(sale.getId());

        SaleItem saleItem1 = new SaleItem();
        saleItem1.setSale(sale);
        saleItem1.setProduct(product);
        saleItem1.setPriceSold(BigDecimal.valueOf(152));
        saleItem1.setQuantitySold(10);

        SaleItem saleItem2 = new SaleItem();
        saleItem2.setSale(sale);
        saleItem2.setProduct(product);
        saleItem2.setPriceSold(BigDecimal.valueOf(152));
        saleItem2.setQuantitySold(10);

        List<SaleItem> saleItems = new ArrayList<>();
        saleItems.add(saleItem1);
        saleItems.add(saleItem2);

        saleItemRepository.saveAll(saleItems);

        Page<SaleItem> saleItemsSaved = saleItemService.getAllSaleItems(Pageable.ofSize(2));

        assertEquals(2, saleItemsSaved.getTotalElements());
    }

    @Test
    @Order(GET_RECORD_BY_ID_TESTING_ORDER)
    @DisplayName(GET_RECORD_BY_ID_TESTING_DISPLAY_NAME)
    void getSaleItemById() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(5200));
        product.setSalePrice(BigDecimal.valueOf(5500));
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

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setAddress(address);
        customer.setPhone("(123)456-7890");
        customer = customerService.saveCustomer(customer);
        assertNotNull(customer.getId());

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDate.now());
        sale = saleService.saveSale(sale);
        assertNotNull(sale.getId());

        SaleItem saleItem1 = new SaleItem();
        saleItem1.setSale(sale);
        saleItem1.setPriceSold(BigDecimal.valueOf(152));
        saleItem1.setProduct(product);
        saleItem1.setQuantitySold(10);

        SaleItem saleItem2 = new SaleItem();
        saleItem2.setSale(sale);
        saleItem2.setProduct(product);
        saleItem2.setPriceSold(BigDecimal.valueOf(152));
        saleItem2.setQuantitySold(10);

        List<SaleItem> saleItems = new ArrayList<>();
        saleItems.add(saleItem1);
        saleItems.add(saleItem2);

        saleItemRepository.saveAll(saleItems);

        SaleItem saleItem = saleItemService.getSaleItemById(1L);

        assertNotNull(saleItem);
    }

    @Test
    @Order(DELETE_RECORD_TESTING_ORDER)
    @DisplayName(DELETE_RECORD_TESTING_DISPLAY_NAME)
    void deleteSaleItem() throws IllegalStateException{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategory = productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(5200));
        product.setSalePrice(BigDecimal.valueOf(5500));
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

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setAddress(address);
        customer.setPhone("(123)456-7890");
        customer = customerService.saveCustomer(customer);
        assertNotNull(customer.getId());

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDate.now());
        sale = saleService.saveSale(sale);
        assertNotNull(sale.getId());

        SaleItem saleItem1 = new SaleItem();
        saleItem1.setSale(sale);
        saleItem1.setProduct(product);
        saleItem1.setPriceSold(BigDecimal.valueOf(152));
        saleItem1.setQuantitySold(10);

        SaleItem saleItem2 = new SaleItem();
        saleItem2.setSale(sale);
        saleItem2.setProduct(product);
        saleItem2.setPriceSold(BigDecimal.valueOf(145));
        saleItem2.setQuantitySold(10);

        List<SaleItem> saleItems = new ArrayList<>();
        saleItems.add(saleItem1);
        saleItems.add(saleItem2);

        saleItemRepository.saveAll(saleItems);
        saleItemService.deleteSaleItem(1L);
        assertThrows(IllegalStateException.class, ()->{
            saleItemService.getSaleItemById(1L);
        });
    }

    @AfterEach
    public void clearDatabase() {
        saleItemRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE sale_items ALTER COLUMN id RESTART WITH 1");
    }

    @AfterAll
    public static void setUpAfterAll(){}
}