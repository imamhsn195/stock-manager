package me.imamhasan.stockmanager.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import me.imamhasan.stockmanager.model.ProductCategory;
import me.imamhasan.stockmanager.service.ProductCategoryServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.imamhasan.stockmanager.config.SwaggerPayloads.ADDRESS_POST_REQUEST_BODY;

@RestController
@RequestMapping("/api/productCategories")
@Api(value = "ProductCategory Management System", tags = { "ProductCategories" }, description = "Operations pertaining to productCategory in ProductCategory Management System")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryServiceImpl productCategoryService;

    @GetMapping
    @ApiOperation(value = "Get all productCategories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved productCategories"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })

    public Page<ProductCategory> getAllProductCategories(
        @ApiParam(value = "Page number", example = "0") @RequestParam(defaultValue = "0") int pageNumber,
        @ApiParam(value = "Page size", example = "10") @RequestParam(defaultValue = "10") int pageSize,
        @ApiParam(value = "Sort field", example = "city") @RequestParam(defaultValue = "id") String sortBy) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
    return productCategoryService.getAllProductCategories(pageable);
}
    @GetMapping("/{id}")
    @ApiOperation(value = "Get productCategory by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved productCategory"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ProductCategory getProductCategoryById(@PathVariable Long id) {
        return productCategoryService.getProductCategoryById(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a new productCategory", notes = ADDRESS_POST_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added productCategory"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ProductCategory addProductCategory(@RequestBody ProductCategory productCategory) {
        return productCategoryService.saveProductCategory(productCategory);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing productCategory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated productCategory"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ProductCategory updateProductCategory(@PathVariable Long id, @RequestBody ProductCategory productCategory) {
        productCategory.setId(id);
        return productCategoryService.updateProductCategory(productCategory);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an existing productCategory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted productCategory"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<?> deleteProductCategory(@PathVariable Long id) {
        productCategoryService.deleteProductCategory(id);
        return ResponseEntity.ok("ProductCategory deleted successfully");
    }
}
