package me.imamhasan.stockmanager.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.service.AddressService;
import me.imamhasan.stockmanager.service.AddressServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.imamhasan.stockmanager.config.SwaggerPayloads.ADDRESS_POST_REQUEST_BODY;
import static me.imamhasan.stockmanager.config.SwaggerPayloads.ADDRESS_PUT_REQUEST_BODY;

@RestController
@RequestMapping("/api/addresses")
@Api(value = "Address Management System", tags = { "Addresses" }, description = "Operations pertaining to address in Address Management System")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
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

    public Page<Address> getAllAddresses(
        @RequestParam(defaultValue = "true") boolean paginated,
        @ApiParam(value = "Page number", example = "0") @RequestParam(defaultValue = "0") int pageNumber,
        @ApiParam(value = "Page size", example = "10") @RequestParam(defaultValue = "10") int pageSize,
        @ApiParam(value = "Sort field", example = "city") @RequestParam(required = false, defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        if (!paginated) { pageable = Pageable.unpaged();}
    return addressService.getAllAddresses(pageable);
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
    @ApiOperation(value = "Add a new address", notes = ADDRESS_POST_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added address"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Address addAddress(@RequestBody Address address) {
        return addressService.saveAddress(address);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing address", notes = ADDRESS_PUT_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated address"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Address updateAddress(@PathVariable Long id, @RequestBody Address address) {
        address.setId(id);
        return addressService.updateAddress(address);
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
