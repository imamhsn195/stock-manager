package me.imamhasan.stockmanager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.service.AddressService;
import me.imamhasan.stockmanager.service.AddressServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@Api(value = "Address Management System", tags = { "Addresses" }, description = "Operations pertaining to address in Address Management System")
@RequiredArgsConstructor
public class AddressController {
    private final AddressServiceImpl addressService;

    @GetMapping
    @ApiOperation(value = "Get all addresses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved addresses"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get address by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved address"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Address getAddressById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a new address")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added address"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<?> addAddress(@RequestBody Address address) {
        addressService.saveAddress(address);
        return ResponseEntity.status(HttpStatus.CREATED).body("Address added successfully");
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated address"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        addressService.updateAddress(address);
        return ResponseEntity.ok("Address updated successfully");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an existing address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted address"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok("Address deleted successfully");
    }
}
