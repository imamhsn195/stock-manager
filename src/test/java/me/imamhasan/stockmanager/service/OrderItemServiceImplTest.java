package me.imamhasan.stockmanager.service;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

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

    @BeforeAll
    public static void setUpBeforeAll() {

    }

    @BeforeEach
    public void beforeEachTest() {}

    @Test
    void saveOrderItem() {
        fail("need to added code.");
    }

    @Test
    void getAllOrderItems() {
        fail("need to added code.");
    }

    @Test
    void getOrderItemById() {
        fail("need to added code.");
    }

    @Test
    void deleteOrderItem() {
        fail("need to added code.");
    }

    @Test
    void updateOrderItem() {
        fail("need to added code.");
    }

    @AfterEach
    public void clearDatabase() {
        orderItemRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE order_items ALTER COLUMN id RESTART WITH 1");
    }

    @AfterAll
    public static void setUpAfterAll(){}
}