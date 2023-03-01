package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Product;
import me.imamhasan.stockmanager.model.ProductCategory;
import me.imamhasan.stockmanager.repository.ProductCategoryRepository;
import me.imamhasan.stockmanager.repository.ProductRepository;
import me.imamhasan.stockmanager.service.ProductServiceImpl;
import org.junit.Assert;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductServiceImplTest {

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
    public void testGetAllProducts() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategoryRepository.save(productCategory);

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPrice(BigDecimal.valueOf(5200));
        product.setProductCategory(productCategory);
        product.setQuantity(5);

        Product product1 = new Product();
        product1.setName("Apple AirPods Pro");
        product1.setDescription("The Apple AirPods Pro are wireless earbuds");
        product1.setPrice(BigDecimal.valueOf(4500));
        product1.setQuantity(10);
        product1.setProductCategory(productCategory);

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
        Assert.assertEquals(new BigDecimal("5200"), new BigDecimal(savedProduct.getPrice().toString()));
        Assert.assertEquals(product.getQuantity(), savedProduct.getQuantity());

        Product savedProduct1 = savedProducts.getContent().get(1);

        Assert.assertEquals(product1.getId(), savedProduct1.getId());
        Assert.assertEquals(product1.getName(), savedProduct1.getName());
        Assert.assertEquals(product1.getDescription(), savedProduct1.getDescription());
        Assert.assertEquals(new BigDecimal("4500"), savedProduct1.getPrice());
        Assert.assertEquals(product1.getQuantity(), savedProduct1.getQuantity());

        Assert.assertEquals(products.size(), savedProducts.getSize());
    }

    @Test
    public void testGetProductById() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategoryRepository.save(productCategory);

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPrice(BigDecimal.valueOf(5200));
        product.setQuantity(5);
        product.setProductCategory(productCategory);
        productRepository.save(product);
        assertNotNull(product.getId());

        Product savedProduct = productService.getProductById(product.getId());

        Assert.assertNotNull(savedProduct);
        Assert.assertEquals(product.getName(), savedProduct.getName());
        Assert.assertEquals(new BigDecimal("5200"), new BigDecimal(product.getPrice().toString()));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetProductByIdNotFound() {
        productService.getProductById(999L);
    }

    @Test
    public void testAddProduct() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategoryRepository.save(productCategory);

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPrice(BigDecimal.valueOf(5200));
        product.setQuantity(5);
        product.setProductCategory(productCategory);
        Product savedProduct = productService.addProduct(product);

        Assert.assertNotNull(savedProduct.getId());
        Assert.assertNotNull(savedProduct.getProductCategory().getId());

        Assert.assertEquals(product.getName(), savedProduct.getName());
        Assert.assertEquals(new BigDecimal(String.valueOf(product.getPrice())), new BigDecimal(savedProduct.getPrice().toString()));
        Assert.assertEquals(product.getId().toString(), savedProduct.getId().toString());
    }

    @Test
    public void testUpdateProduct() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategoryRepository.save(productCategory);

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPrice(BigDecimal.valueOf(5200));
        product.setQuantity(5);
        product.setProductCategory(productCategory);
        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());
        assertNotNull(savedProduct.getProductCategory().getId());

        assertEquals("Dell XPS 13", savedProduct.getName());
        assertEquals("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability", savedProduct.getDescription());
        assertEquals(new BigDecimal(5200), savedProduct.getPrice());
        assertEquals(Integer.valueOf("5"), savedProduct.getQuantity());
        assertEquals(product.getId(), savedProduct.getId());

        ProductCategory productCategoryNew = new ProductCategory();
        productCategoryNew.setName("Product category two");

        savedProduct.setProductCategory(productCategoryNew);
        savedProduct.setName("Apple AirPods Pro");
        savedProduct.setDescription("The Apple AirPods Pro are wireless earbuds");
        savedProduct.setPrice(BigDecimal.valueOf(4500));
        savedProduct.setQuantity(10);

        Product updatedProduct = productService.updateProduct(savedProduct);

        Assert.assertNotNull(updatedProduct.getId());
        Assert.assertNotNull(updatedProduct.getProductCategory().getId());

        assertEquals("Apple AirPods Pro", savedProduct.getName());
        assertEquals(updatedProduct.getProductCategory().getId(), savedProduct.getProductCategory().getId());
        assertEquals("Product category two", savedProduct.getProductCategory().getName());
        assertEquals("The Apple AirPods Pro are wireless earbuds", savedProduct.getDescription());
        assertEquals(new BigDecimal(4500), savedProduct.getPrice());
        assertEquals(Integer.valueOf("10"), savedProduct.getQuantity());
    }

    @Test(expected = IllegalStateException.class)
    public void testUpdateProductNotFound() {

        Long id = 100L;
        Product product = new Product();
        product.setId(id);
        productService.updateProduct(product);

    }

    @Test
    public void testDeleteProduct() throws IllegalStateException{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Electronics");
        productCategoryRepository.save(productCategory);

        Product product = new Product();
        product.setName("Dell XPS 13");
        product.setDescription("The Dell XPS 13 is a high-end ultrabook laptop designed for performance and portability");
        product.setPrice(BigDecimal.valueOf(5200));
        product.setQuantity(5);
        product.setProductCategory(productCategory);
        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());

        productService.deleteProduct(1L);

        assertThrows(IllegalStateException.class, ()->{
            productService.getProductById(1L);
        });
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteProductNotFound() {
        productService.deleteProduct(999L);
    }

    @AfterEach
    public void clearDatabase() {
        productRepository.deleteAll();
        applicationContext.getBean(JdbcTemplate.class).execute("ALTER TABLE product ALTER COLUMN id RESTART WITH 1");
    }

    @AfterAll
    public static void setUpAfterAll(){}
}