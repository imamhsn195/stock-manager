package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Customer;
import me.imamhasan.stockmanager.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static config.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName(CUSTOMER_TESTING_CLASS_DISPLAY_NAME)
class CustomerServiceImplTest {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    AddressService addressService;

    @BeforeAll
    public static void clearProductTable() {}
    @BeforeEach
    public void beforeEachTest() {}
    @Test
    @Order(SAVE_RECORD_TESTING_ORDER)
    @DisplayName(SAVE_RECORD_TESTING_DISPLAY_NAME)
    public void testSaveCustomer() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("(123)456-7890");
        customer.setAddress(address);

        Customer savedCustomer = customerService.saveCustomer(customer);
        assertNotNull(savedCustomer);
        assertEquals("John Doe", savedCustomer.getName());
        assertEquals("johndoe@example.com", savedCustomer.getEmail());
        assertEquals("(123)456-7890", savedCustomer.getPhone());
        assertEquals(address.getId(), savedCustomer.getAddress().getId());
    }

    @Test
    @Order(GET_SAVED_RECORDS_TESTING_ORDER)
    @DisplayName(GET_RECORD_TESTING_DISPLAY_NAME)
    public void testFindAllCustomers() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        addressService.saveAddress(address);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("(123)456-7890");
        assertNotNull(address.getId());
        customer.setAddress(address);
        customerService.saveCustomer(customer);

        Page<Customer> customers = customerService.getAllCustomers(Pageable.ofSize(1));
        assertNotNull(customers);

        assertEquals(1, customers.getTotalElements());
    }

    @Test
    @Order(GET_RECORD_BY_ID_TESTING_ORDER)
    @DisplayName(GET_RECORD_BY_ID_TESTING_DISPLAY_NAME)
    public void getCustomerByIdTest() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        addressService.saveAddress(address);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("(123)456-7890");
        assertNotNull(address.getId());
        customer.setAddress(address);
        customerService.saveCustomer(customer);

        Customer customerSaved = customerService.getCustomerById(1L);
        assertNotNull(customerSaved);
    }

    @Test
    @Order(UPDATE_RECORD_TESTING_ORDER)
    @DisplayName(UPDATE_RECORD_TESTING_DISPLAY_NAME)
    public void testUpdateCustomer() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        address = addressService.saveAddress(address);

        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("(123)456-7890");
        customer.setAddress(address);
        Customer savedCustomer = customerService.saveCustomer(customer);
        assertNotNull(address.getId());
        assertNotNull(savedCustomer.getId());

        Address newAddress = new Address();
        newAddress.setStreet("123 Main St");
        newAddress.setCity("Anytown");
        newAddress.setState("CA");
        newAddress.setCountry("USA");
        newAddress.setZipCode("12345");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(savedCustomer.getId());
        updatedCustomer.setName("Jane Smith");
        updatedCustomer.setEmail("janesmith@example.com");
        updatedCustomer.setPhone("(987)654-3210");
        updatedCustomer.setAddress(address);

        Customer result = customerService.updateCustomer(updatedCustomer);

        assertNotNull(result);
        assertEquals("Jane Smith", result.getName());
        assertEquals("janesmith@example.com", result.getEmail());
        assertEquals("(987)654-3210", result.getPhone());
        assertEquals(address.getId(), result.getAddress().getId());
    }

    @Test
    @Order(DELETE_RECORD_TESTING_ORDER)
    @DisplayName(DELETE_RECORD_TESTING_DISPLAY_NAME)
    public void testDeleteCustomer() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setCountry("USA");
        address.setZipCode("12345");
        addressService.saveAddress(address);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("(123)456-7890");
        assertNotNull(address.getId());
        customer.setAddress(address);
        Customer savedCustomer = customerService.saveCustomer(customer);
        customerService.deleteCustomer(savedCustomer.getId());
        assertThrows(IllegalStateException.class, ()->{
            customerService.getCustomerById(savedCustomer.getId());
        });
    }
    @AfterEach
    public void setUpAfterEach() {
        customerRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE customers ALTER COLUMN id RESTART WITH 1");
    }
    @AfterAll
    public static void setUpAfterAll(){}
}