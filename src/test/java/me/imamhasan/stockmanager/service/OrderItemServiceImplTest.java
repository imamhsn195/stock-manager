package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.*;
import me.imamhasan.stockmanager.model.Order;
import me.imamhasan.stockmanager.repository.OrderItemRepository;
import me.imamhasan.stockmanager.repository.OrderRepository;
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
class OrderItemServiceImplTest {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    AddressServiceImpl addressService;
    @Autowired
    CustomerService customerService;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    OrderItemServiceImpl orderItemService;

    @BeforeAll
    public static void setUpBeforeAll() {

    }

    @BeforeEach
    public void beforeEachTest() {}

    @Test
    void saveOrderItem() {
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

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setAddress(address);
        customer.setPhone("(123)456-7890");
        customer = customerService.saveCustomer(customer);
        assertNotNull(customer.getId());

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order = orderService.saveOrder(order);
        assertNotNull(order.getId());

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantityOrdered(10);
        orderItem = orderItemService.saveOrderItem(orderItem);
        assertNotNull(orderItem.getId());
    }

    @Test
    void getAllOrderItems() {
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

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setAddress(address);
        customer.setPhone("(123)456-7890");
        customer = customerService.saveCustomer(customer);
        assertNotNull(customer.getId());

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order = orderService.saveOrder(order);
        assertNotNull(order.getId());

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrder(order);
        orderItem1.setProduct(product);
        orderItem1.setQuantityOrdered(10);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrder(order);
        orderItem2.setProduct(product);
        orderItem2.setQuantityOrdered(10);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        orderItemRepository.saveAll(orderItems);

        Page<OrderItem> orderItemsSaved = orderItemService.getAllOrderItems(Pageable.ofSize(2));

        assertEquals(2, orderItemsSaved.getTotalElements());
    }

    @Test
    void getOrderItemById() {
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

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setAddress(address);
        customer.setPhone("(123)456-7890");
        customer = customerService.saveCustomer(customer);
        assertNotNull(customer.getId());

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order = orderService.saveOrder(order);
        assertNotNull(order.getId());

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrder(order);
        orderItem1.setProduct(product);
        orderItem1.setQuantityOrdered(10);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrder(order);
        orderItem2.setProduct(product);
        orderItem2.setQuantityOrdered(10);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        orderItemRepository.saveAll(orderItems);

        OrderItem orderItem = orderItemService.getOrderItemById(1L);

        assertNotNull(orderItem);
    }

    @Test
    void deleteOrderItem() throws IllegalStateException{
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

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setAddress(address);
        customer.setPhone("(123)456-7890");
        customer = customerService.saveCustomer(customer);
        assertNotNull(customer.getId());

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order = orderService.saveOrder(order);
        assertNotNull(order.getId());

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrder(order);
        orderItem1.setProduct(product);
        orderItem1.setQuantityOrdered(10);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrder(order);
        orderItem2.setProduct(product);
        orderItem2.setQuantityOrdered(10);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        orderItemRepository.saveAll(orderItems);
        orderItemService.deleteOrderItem(1L);
        assertThrows(IllegalStateException.class, ()->{
            orderItemService.getOrderItemById(1L);
        });
    }

    @Test
    void updateOrderItem() {
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

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setAddress(address);
        customer.setPhone("(123)456-7890");
        customer = customerService.saveCustomer(customer);
        assertNotNull(customer.getId());

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order = orderService.saveOrder(order);
        assertNotNull(order.getId());

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantityOrdered(10);
        orderItemRepository.save(orderItem);
        assertNotNull(orderItem.getId());

        OrderItem orderItemSaved = orderItemService.getOrderItemById(1L);
        assertEquals(10, orderItemSaved.getQuantityOrdered());

        orderItemSaved.setQuantityOrdered(15);
        orderItemService.updateOrderItem(orderItemSaved);

        OrderItem orderItemUpdated = orderItemService.getOrderItemById(1L);

        assertEquals(15, orderItemSaved.getQuantityOrdered());

    }

    @AfterEach
    public void clearDatabase() {
        orderItemRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE order_items ALTER COLUMN id RESTART WITH 1");
    }

    @AfterAll
    public static void setUpAfterAll(){}
}