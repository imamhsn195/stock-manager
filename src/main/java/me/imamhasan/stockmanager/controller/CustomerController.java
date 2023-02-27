package me.imamhasan.stockmanager.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import me.imamhasan.stockmanager.model.Customer;
import me.imamhasan.stockmanager.service.CustomerServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static me.imamhasan.stockmanager.config.SwaggerPayloads.SUPPLIER_POST_REQUEST_BODY;

@RestController
@RequestMapping("/api/customers")
@Api(value = "Customer Management System", tags = { "Customers" }, description = "Operations pertaining to customer in Customer Management System")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @GetMapping
    @ApiOperation(value = "Get all customers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved customers"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Page<Customer> getAllCustomers(
            @ApiParam(value = "Page number", example = "0") @RequestParam(defaultValue = "0") int pageNumber,
            @ApiParam(value = "Page size", example = "10") @RequestParam(defaultValue = "10") int pageSize,
            @ApiParam(value = "Sort field", example = "name") @RequestParam(defaultValue = "name") String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return customerService.getAllCustomers(pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get customer by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved customer"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a new customer", notes = SUPPLIER_POST_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added customer"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Customer addCustomer(@Valid @RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated customer"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setId(id);
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an existing customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted customer"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}
