package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Product;
import me.imamhasan.stockmanager.model.ProductCategory;
import me.imamhasan.stockmanager.repository.ProductCategoryRepository;
import me.imamhasan.stockmanager.repository.ProductRepository;
import org.junit.Assert;
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
import java.util.ArrayList;
import java.util.List;

import static config.TestConstants.*;
import static org.junit.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName(PRODUCT_TESTING_CLASS_DISPLAY_NAME)
class ProductServiceImplTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductServiceImpl productService;

    @BeforeAll
    public static void clearProductTable() {}
    @BeforeEach
    public void beforeEachTest() {}

    @Test
    @Order(SAVE_RECORD_TESTING_ORDER)
    @DisplayName(SAVE_RECORD_TESTING_DISPLAY_NAME)
    public void testSaveProduct() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategoryRepository.save(productCategory);

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(5200));
        product.setSalePrice(BigDecimal.valueOf(5500));
        product.setQuantity(5);
        product.setProductCategory(productCategory);
        Product savedProduct = productService.addProduct(product);

        Assert.assertNotNull(savedProduct.getId());
        Assert.assertNotNull(savedProduct.getProductCategory().getId());

        Assert.assertEquals(product.getName(), savedProduct.getName());
        Assert.assertEquals(new BigDecimal(String.valueOf(product.getPurchasePrice())), new BigDecimal(savedProduct.getPurchasePrice().toString()));
        Assert.assertEquals(new BigDecimal(String.valueOf(product.getSalePrice())), new BigDecimal(savedProduct.getSalePrice().toString()));
        Assert.assertEquals(product.getId().toString(), savedProduct.getId().toString());
    }

    @Test
    @Order(GET_SAVED_RECORDS_TESTING_ORDER)
    @DisplayName(GET_RECORD_TESTING_DISPLAY_NAME)
    public void testGetAllProducts() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategoryRepository.save(productCategory);

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(5200));
        product.setSalePrice(BigDecimal.valueOf(5500));
        product.setProductCategory(productCategory);
        product.setQuantity(5);

        Product product1 = new Product();
        product1.setName("Apple AirPods Pro");
        product1.setDescription("The Apple AirPods Pro are wireless earbuds");
        product1.setPurchasePrice(BigDecimal.valueOf(4200));
        product1.setSalePrice(BigDecimal.valueOf(4500));
        product1.setProductCategory(productCategory);
        product1.setQuantity(10);

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product1);

        productRepository.saveAll(products);

        Page<Product> savedProducts = productService.getAllProducts(Pageable.ofSize(2));

        assertNotNull(savedProducts);

        Product savedProduct = savedProducts.getContent().get(0);

        Assert.assertEquals(product.getId(), savedProduct.getId());
        Assert.assertEquals(product.getName(), savedProduct.getName());
        Assert.assertEquals(product.getDescription(), savedProduct.getDescription());
        Assert.assertEquals(new BigDecimal("5200"), new BigDecimal(savedProduct.getPurchasePrice().toString()));
        Assert.assertEquals(new BigDecimal("5500"), new BigDecimal(savedProduct.getSalePrice().toString()));
        Assert.assertEquals(product.getQuantity(), savedProduct.getQuantity());

        Product savedProduct1 = savedProducts.getContent().get(1);

        Assert.assertEquals(product1.getId(), savedProduct1.getId());
        Assert.assertEquals(product1.getName(), savedProduct1.getName());
        Assert.assertEquals(product1.getDescription(), savedProduct1.getDescription());
        Assert.assertEquals(new BigDecimal("4200"), savedProduct1.getPurchasePrice());
        Assert.assertEquals(new BigDecimal("4500"), savedProduct1.getSalePrice());
        Assert.assertEquals(product1.getQuantity(), savedProduct1.getQuantity());

        Assert.assertEquals(products.size(), savedProducts.getSize());
    }

    @Test
    @Order(GET_RECORD_BY_ID_TESTING_ORDER)
    @DisplayName(GET_RECORD_BY_ID_TESTING_DISPLAY_NAME)
    public void testGetProductById() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategoryRepository.save(productCategory);

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(5200));
        product.setSalePrice(BigDecimal.valueOf(5500));
        product.setQuantity(5);
        product.setProductCategory(productCategory);
        productRepository.save(product);
        assertNotNull(product.getId());

        Product savedProduct = productService.getProductById(product.getId());

        Assert.assertNotNull(savedProduct);
        Assert.assertEquals(product.getName(), savedProduct.getName());
        Assert.assertEquals(new BigDecimal("5200"), new BigDecimal(product.getPurchasePrice().toString()));
        Assert.assertEquals(new BigDecimal("5500"), new BigDecimal(product.getSalePrice().toString()));
    }

    @Test()
    public void testGetProductByIdNotFound() throws IllegalStateException {
        assertThrows(IllegalStateException.class, ()->{
            productService.getProductById(999L);
        });
    }

    @Test
    @Order(UPDATE_RECORD_TESTING_ORDER)
    @DisplayName(UPDATE_RECORD_TESTING_DISPLAY_NAME)
    public void testUpdateProduct() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategoryRepository.save(productCategory);

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(5200));
        product.setSalePrice(BigDecimal.valueOf(5500));
        product.setQuantity(5);
        product.setProductCategory(productCategory);
        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());
        assertNotNull(savedProduct.getProductCategory().getId());

        assertEquals("Dell XPS 13", savedProduct.getName());
        assertEquals("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability", savedProduct.getDescription());
        assertEquals(new BigDecimal(5200), savedProduct.getPurchasePrice());
        assertEquals(new BigDecimal(5500), savedProduct.getSalePrice());
        assertEquals(Integer.valueOf("5"), savedProduct.getQuantity());
        assertEquals(product.getId(), savedProduct.getId());

        ProductCategory productCategoryNew = new ProductCategory();
        productCategoryNew.setName("Product category two");

        savedProduct.setProductCategory(productCategoryNew);
        savedProduct.setName("Apple AirPods Pro");
        savedProduct.setDescription("The Apple AirPods Pro are wireless earbuds");
        savedProduct.setPurchasePrice(BigDecimal.valueOf(4200));
        savedProduct.setSalePrice(BigDecimal.valueOf(4500));
        savedProduct.setQuantity(10);

        Product updatedProduct = productService.updateProduct(savedProduct);

        Assert.assertNotNull(updatedProduct.getId());
        Assert.assertNotNull(updatedProduct.getProductCategory().getId());

        assertEquals("Apple AirPods Pro", savedProduct.getName());
        assertEquals(updatedProduct.getProductCategory().getId(), savedProduct.getProductCategory().getId());
        assertEquals("Product category two", savedProduct.getProductCategory().getName());
        assertEquals("The Apple AirPods Pro are wireless earbuds", savedProduct.getDescription());
        assertEquals(new BigDecimal(4200), savedProduct.getPurchasePrice());
        assertEquals(new BigDecimal(4500), savedProduct.getSalePrice());
        assertEquals(Integer.valueOf("10"), savedProduct.getQuantity());
    }

    @Test()

    public void testUpdateProductNotFound() throws IllegalStateException {
        Long id = 100L;
        Product product = new Product();
        product.setId(id);
        assertThrows(IllegalStateException.class, ()-> {
            productService.updateProduct(product);
        });
    }

    @Test
    @Order(DELETE_RECORD_TESTING_ORDER)
    @DisplayName(DELETE_RECORD_TESTING_DISPLAY_NAME)
    public void testDeleteProduct() throws IllegalStateException{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategoryRepository.save(productCategory);

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPurchasePrice(BigDecimal.valueOf(5200));
        product.setSalePrice(BigDecimal.valueOf(5500));
        product.setQuantity(5);
        product.setProductCategory(productCategory);
        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());

        productService.deleteProduct(1L);

        assertThrows(IllegalStateException.class, ()->{
            productService.getProductById(1L);
        });
    }

    @Test()
    public void testDeleteProductNotFound() throws IllegalStateException {
        assertThrows(IllegalStateException.class, ()-> {
            productService.deleteProduct(999L);
        });
    }

    @AfterEach
    public void setUpAfterEach() {
        productRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE products ALTER COLUMN id RESTART WITH 1");
    }

    @AfterAll
    public static void setUpAfterAll(){}
}