package me.imamhasan.stockmanager.controller;

import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import me.imamhasan.stockmanager.config.SwaggerPayloads;
import me.imamhasan.stockmanager.model.Product;
import me.imamhasan.stockmanager.service.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static me.imamhasan.stockmanager.config.SwaggerPayloads.*;

@RestController
@RequestMapping("/api/products")
@Api(value = "Product Management System", tags = { "Products"}, description = "Operations pertaining to product in Product Management System")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    @GetMapping
    @ApiOperation(value = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved products"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Page<Product> getAllProducts(
            @ApiParam(value = "Page number", example = "0") @RequestParam(defaultValue = "0") int pageNumber,
            @ApiParam(value = "Page size", example = "10") @RequestParam(defaultValue = "10") int pageSize,
            @ApiParam(value = "Sort field", example = "quantity") @RequestParam(defaultValue = "quantity") String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    @ApiParam(value = "ID of the product to retrieve", required = true)
    @ApiOperation(value = "Get product by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved product"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a new product", notes = PRODUCT_POST_REQUEST_BODY)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully added product"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Product addProduct(@Valid @RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    @ApiParam(value = "ID of the product to update", required = true)
    @ApiOperation(value = "Update an existing product", response = Product.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated product"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        product.setId(id);
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    @ApiParam(value = "ID of the product to delete", required = true)
    @ApiOperation(value = "Delete a product by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted product"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
