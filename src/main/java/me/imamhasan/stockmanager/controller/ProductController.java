package me.imamhasan.stockmanager.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.imamhasan.stockmanager.model.Product;
import me.imamhasan.stockmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Api(value = "Product Management System", description = "Operations pertaining to product in Product Management System")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "View a list of available products", response = List.class)
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    @ApiOperation(value = "Get a product by ID", response = Product.class)
    @ApiParam(value = "ID of the product to retrieve", required = true)
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @ApiOperation(value = "Create a new product", response = Product.class)
    @ApiParam(value = "Details of the product to create", required = true)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }


    @ApiOperation(value = "Update an existing product", response = Product.class)
    @ApiParam(value = "ID of the product to update", required = true)
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productService.updateProduct(product);
    }

    @ApiOperation(value = "Delete a product by ID")
    @ApiParam(value = "ID of the product to delete", required = true)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
