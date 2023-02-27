package me.imamhasan.stockmanager.service.test.java.me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Customer;
import me.imamhasan.stockmanager.model.Order;
import me.imamhasan.stockmanager.model.Product;
import me.imamhasan.stockmanager.repository.OrderRepository;
import me.imamhasan.stockmanager.service.AddressServiceImpl;
import me.imamhasan.stockmanager.service.CustomerService;
import me.imamhasan.stockmanager.service.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    CustomerService customerService;
    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AddressServiceImpl addressService;

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
        orders.add(order);
        orderRepository.saveAll(orders);

        Page<Order> savedOrders = orderService.getAllOrders(Pageable.ofSize(2));

        assertNotNull(savedOrders);

        fail("Need to complete following product list");

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
        fail("Need to add testing codes");
    }
}