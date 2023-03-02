package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Customer;
import me.imamhasan.stockmanager.model.Sale;
import me.imamhasan.stockmanager.repository.SaleRepository;
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

import static org.junit.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SaleServiceImplTest {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CustomerService customerService;
    @Autowired
    SaleServiceImpl saleService;

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    AddressServiceImpl addressService;

    @BeforeAll
    public static void clearProductTable() {}
    @BeforeEach
    public void beforeEachTest() {}

    @Test()
    void saveSale() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        address = addressService.saveAddress(address);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("(123)456-7890");

        Assertions.assertNotNull(address.getId());

        customer.setAddress(address);

        // save the customer
        customer = customerService.saveCustomer(customer);

        // create a sale with the customer ID
        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDate.now());

        // save the sale
        sale = saleService.saveSale(sale);

        // assert that the sale was saved successfully
        assertNotNull(sale.getId());
    }

    @Test
    void getAllSales() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        address = addressService.saveAddress(address);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("(123)456-7890");

        Assertions.assertNotNull(address.getId());

        customer.setAddress(address);

        // save the customer
        customer = customerService.saveCustomer(customer);

        // create a sale with the customer ID
        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDate.now());

        Sale sale1 = new Sale();
        sale1.setCustomer(customer);
        sale1.setSaleDate(LocalDate.now());

        List<Sale> sales = new ArrayList<>();
        sales.add(sale);
        sales.add(sale1);
        saleRepository.saveAll(sales);
        Page<Sale> savedSales = saleService.getAllSales(Pageable.ofSize(2));
        assertEquals(2, savedSales.getTotalElements());
    }

    @Test
    void getSaleById() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        address = addressService.saveAddress(address);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("(123)456-7890");

        Assertions.assertNotNull(address.getId());

        customer.setAddress(address);

        // save the customer
        customer = customerService.saveCustomer(customer);

        // create a sale with the customer ID
        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDate.now());

        // save the sale
        sale = saleRepository.save(sale);

        assertNotNull(saleService.getSaleById(sale.getId()));
    }

    @Test
    void deleteSale() throws IllegalStateException{
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        address = addressService.saveAddress(address);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("(123)456-7890");

        Assertions.assertNotNull(address.getId());

        customer.setAddress(address);

        // save the customer
        customer = customerService.saveCustomer(customer);

        // create a sale with the customer ID
        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDate.now());

        // save the sale
        sale = saleService.saveSale(sale);

        // assert that the sale was saved successfully
        assertNotNull(sale.getId());

        Long id = sale.getId();
        saleService.deleteSale(sale.getId());

        assertThrows(IllegalStateException.class, ()->{
            saleService.getSaleById(id);
        });

    }

    @Test
    void updateSale() {
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
        customer.setPhone("(123)456-7890");

        Assertions.assertNotNull(address.getId());

        customer.setAddress(address);

        // save the customer
        customer = customerService.saveCustomer(customer);
        assertNotNull(customer.getId());

        // create a sale with the customer ID
        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDate.parse("2023-01-20"));

        // save the sale
        sale = saleService.saveSale(sale);

        // assert that the sale was saved successfully
        assertNotNull(sale.getId());

        assertEquals("John Doe", sale.getCustomer().getName());

        assertEquals("123 Main St", sale.getCustomer().getAddress().getStreet());

        sale.setSaleDate(sale.getSaleDate().plusDays(2));

        Sale saleUpdated = saleService.updateSale(sale);

        assertEquals(LocalDate.parse("2023-01-22"), saleUpdated.getSaleDate());

    }
    @AfterEach
    public void clearDatabase() {
        saleRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE sales ALTER COLUMN id RESTART WITH 1");
    }
    @AfterAll
    public static void setUpAfterAll(){}
}