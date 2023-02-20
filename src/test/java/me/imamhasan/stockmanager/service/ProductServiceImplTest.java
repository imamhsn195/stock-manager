package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Product;
import me.imamhasan.stockmanager.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Before
    public void setUp() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Product A", "Description A", new BigDecimal("10.00"), 100));
        products.add(new Product(2L, "Product B", "Description B", new BigDecimal("10.00"), 200));
        System.out.println("Products :" + products);
        Mockito.when(productRepository.findAll()).thenReturn(products);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(products.get(0)));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            if (product.getId() == null) {
                product.setId(3L);
            }
            return product;
        });
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = productService.getAllProducts();
        Assert.assertEquals(2, products.size());
        Assert.assertEquals("Product A", products.get(0).getName());
        Assert.assertEquals("Description B", products.get(1).getDescription());
    }

    @Test
    public void testGetProductById() {
        Product product = productService.getProductById(1L);

        Assert.assertNotNull(product);
        Assert.assertEquals("Product A", product.getName());
        Assert.assertEquals(new BigDecimal("10.00"), product.getPrice());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductByIdNotFound() {
        productService.getProductById(999L);
    }

    @Test
    public void testAddProduct() {
        Product newProduct = new Product (3L, "Product C", "Description A", new BigDecimal("30.00"), 100);
        Product savedProduct = productService.addProduct(newProduct);

        Assert.assertNotNull(savedProduct);
        Assert.assertEquals("Product C", savedProduct.getName());
        Assert.assertEquals(new BigDecimal("30.00"), savedProduct.getPrice());
        Assert.assertEquals(3L, savedProduct.getId().longValue());
    }

    @Test
    public void testUpdateProduct() {
        Product updatedProduct = new Product(1L, "Product A Updated", "Description A", new BigDecimal("11.00"), 100);
        Product savedProduct = productService.updateProduct(updatedProduct);

        Assert.assertNotNull(savedProduct);
        Assert.assertEquals("Product A Updated", savedProduct.getName());
        Assert.assertEquals(new BigDecimal("11.00"), savedProduct.getPrice());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateProductNotFound() {
        // Create a product with a known id and some other properties
        Long id = 1L;
        Product product = new Product(id, "Product A", "Description A", new BigDecimal("10.00"), 100);

        // Mock the behavior of the ProductRepository.findById method to return Optional.empty()
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());

        // Call the updateProduct method and expect it to throw a ProductNotFoundException
        productService.updateProduct(product);
    }

    @Test
    public void testDeleteProduct() {
        productService.deleteProduct(1L);

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteProductNotFound() {
        productService.deleteProduct(999L);
    }

}