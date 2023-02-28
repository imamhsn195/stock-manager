package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Customer;
import me.imamhasan.stockmanager.model.Order;
import me.imamhasan.stockmanager.repository.OrderRepository;
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
class OrderServiceImplTest {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CustomerService customerService;
    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AddressServiceImpl addressService;

    @BeforeAll
    public static void clearProductTable() {}
    @BeforeEach
    public void beforeEachTest() {}

    @Test()
    void saveOrder() {
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

        // create an order with the customer ID
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());

        // save the order
        order = orderService.saveOrder(order);

        // assert that the order was saved successfully
        assertNotNull(order.getId());
    }

    @Test
    void getAllOrders() {
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

        // create an order with the customer ID
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());

        Order order1 = new Order();
        order1.setCustomer(customer);
        order1.setOrderDate(LocalDate.now());

        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order1);
        orderRepository.saveAll(orders);
        Page<Order> savedOrders = orderService.getAllOrders(Pageable.ofSize(2));
        assertEquals(2, savedOrders.getTotalElements());
    }

    @Test
    void getOrderById() {
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

        // create an order with the customer ID
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());

        // save the order
        order = orderRepository.save(order);

        assertNotNull(orderService.getOrderById(order.getId()));
    }

    @Test
    void deleteOrder() throws IllegalStateException{
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

        // create an order with the customer ID
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());

        // save the order
        order = orderService.saveOrder(order);

        // assert that the order was saved successfully
        assertNotNull(order.getId());

        Long id = order.getId();
        orderService.deleteOrder(order.getId());

        assertThrows(IllegalStateException.class, ()->{
            orderService.getOrderById(id);
        });

    }

    @Test
    void updateOrder() {
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

        // create an order with the customer ID
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());

        // save the order
        order = orderService.saveOrder(order);

        // assert that the order was saved successfully
        assertNotNull(order.getId());

        assertEquals("John Doe", order.getCustomer().getName());

        assertEquals("123 Main St", order.getCustomer().getAddress().getStreet());


    }
    @AfterEach
    public void clearDatabase() {
        orderRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE orders ALTER COLUMN id RESTART WITH 1");
    }
    @AfterAll
    public static void setUpAfterAll(){}
}