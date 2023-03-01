package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.ProductCategory;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductCategoryServiceImplTest {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ProductCategoryServiceImpl productCategoryService;
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @BeforeAll
    public static void setUpBeforeAll() {}
    @BeforeEach
    public void setUpBeforeEach() {}
    @Test
    void saveProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategoryService.saveProductCategory(productCategory);
        assertNotNull(productCategory.getId());
    }

    @Test
    void getAllProductCategories() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");

        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setName("Clothe");

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(productCategory);
        productCategories.add(productCategory1);
        productCategoryRepository.saveAll(productCategories);

        Page<ProductCategory> savedProductCategories = productCategoryService.getAllProductCategories(Pageable.ofSize(2));

        assertEquals(2, savedProductCategories.getTotalElements());
        ProductCategory first_cat = savedProductCategories.getContent().get(0);
        ProductCategory second_cat = savedProductCategories.getContent().get(1);
        assertEquals("Electronics", first_cat.getName());
        assertEquals("Clothe", second_cat.getName());
    }

    @Test
    void getProductCategoryById() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");

        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setName("Clothe");

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(productCategory);
        productCategories.add(productCategory1);
        productCategoryRepository.saveAll(productCategories);

        ProductCategory first_cat = productCategoryService.getProductCategoryById(1L);
        ProductCategory second_cat = productCategoryService.getProductCategoryById(2L);

        assertEquals("Electronics", first_cat.getName());
        assertEquals("Clothe", second_cat.getName());
    }

    @Test
    void deleteProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");

        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setName("Clothe");

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(productCategory);
        productCategories.add(productCategory1);
        productCategoryRepository.saveAll(productCategories);

        ProductCategory first_cat = productCategoryService.getProductCategoryById(1L);
        ProductCategory second_cat = productCategoryService.getProductCategoryById(2L);

        assertEquals("Electronics", first_cat.getName());
        assertEquals("Clothe", second_cat.getName());

        productCategoryService.deleteProductCategory(1L);

        assertThrows(IllegalStateException.class, ()->{
            productCategoryService.getProductCategoryById(1L);
        });
    }

    @Test
    void updateProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategoryRepository.save(productCategory);
        assertNotNull(productCategory.getId());

        ProductCategory productCategorySaved = productCategoryService.getProductCategoryById(1L);
        productCategorySaved.setName("Clothe");
        productCategoryService.updateProductCategory(productCategorySaved);

        ProductCategory productCategoryUpdated = productCategoryService.getProductCategoryById(1L);
        assertEquals("Clothe", productCategoryUpdated.getName());
    }

    @AfterEach
    public void clearDatabase() {
        productCategoryRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE product_categories ALTER COLUMN id RESTART WITH 1");
    }

    @AfterAll
    public static void setUpAfterAll(){}
}