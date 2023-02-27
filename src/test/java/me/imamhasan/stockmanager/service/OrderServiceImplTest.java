package me.imamhasan.stockmanager.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.fail;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderServiceImplTest {

    @Test()
    void saveOrder() {
        fail("Need to add testing codes");
    }

    @Test
    void getAllOrders() {
        fail("Need to add testing codes");
    }

    @Test
    void getOrderById() {
        fail("Need to add testing codes");
    }

    @Test
    void deleteOrder() {
        fail("Need to add testing codes");
    }

    @Test
    void updateOrder() {
        fail("Need to add testing codes");
    }
}