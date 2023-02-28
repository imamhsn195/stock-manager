package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Customer;
import me.imamhasan.stockmanager.repository.CustomerRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerServiceImplTest {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private AddressService addressService;

    @BeforeAll
    public static void clearProductTable() {}
    @BeforeEach
    public void beforeEachTest() {}
    @Test
    public void testCreateCustomer() {
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
        assertNotNull(savedCustomer);
        assertEquals("John Doe", savedCustomer.getName());
        assertEquals("johndoe@example.com", savedCustomer.getEmail());
        assertEquals("(123)456-7890", savedCustomer.getPhone());
        assertEquals(address.getId(), savedCustomer.getAddress().getId());
    }

    @Test
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
    public void testUpdateCustomer() {
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
        assertNotNull(savedCustomer);

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
    public void clearDatabase() {
        customerRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE customer ALTER COLUMN id RESTART WITH 1");
    }

    @AfterAll
    public static void setUpAfterAll(){}
}