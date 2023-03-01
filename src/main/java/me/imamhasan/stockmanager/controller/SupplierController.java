package me.imamhasan.stockmanager.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import me.imamhasan.stockmanager.model.Supplier;
import me.imamhasan.stockmanager.service.SupplierServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static me.imamhasan.stockmanager.config.SwaggerPayloads.SUPPLIER_POST_REQUEST_BODY;
import static me.imamhasan.stockmanager.config.SwaggerPayloads.SUPPLIER_PUT_REQUEST_BODY;

@RestController
@RequestMapping("/api/suppliers")
@Api(value = "Supplier Management System", tags = { "Suppliers" }, description = "Operations pertaining to supplier in Supplier Management System")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierServiceImpl supplierService;

    @GetMapping
    @ApiOperation(value = "Get all suppliers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved suppliers"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Page<Supplier> getAllSuppliers(
            @ApiParam(value = "Page number", example = "0") @RequestParam(defaultValue = "0") int pageNumber,
            @ApiParam(value = "Page size", example = "10") @RequestParam(defaultValue = "10") int pageSize,
            @ApiParam(value = "Sort field", example = "name") @RequestParam(defaultValue = "name") String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return supplierService.getAllSuppliers(pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get supplier by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved supplier"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Supplier getSupplierById(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a new supplier", notes = SUPPLIER_POST_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added supplier"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Supplier addSupplier(@Valid @RequestBody Supplier supplier) {
        return supplierService.saveSupplier(supplier);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing supplier", notes = SUPPLIER_PUT_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated supplier"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Supplier updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        supplier.setId(id);
        return supplierService.updateSupplier(supplier);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an existing supplier")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted supplier"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok("Supplier deleted successfully");
    }
}
