package me.imamhasan.stockmanager.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import me.imamhasan.stockmanager.model.Purchase;
import me.imamhasan.stockmanager.service.PurchaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.imamhasan.stockmanager.config.SwaggerPayloads.*;

@RestController
@RequestMapping("/api/purchases")
@Api(value = "Purchase Management System", tags = { "Purchases" }, description = "Operations pertaining to purchase in Purchase Management System")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseServiceImpl purchaseService;

    @GetMapping
    @ApiOperation(value = "Get all purchases")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved purchases"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })

    public Page<Purchase> getAllPurchases(
        @ApiParam(value = "Page number", example = "0") @RequestParam(defaultValue = "0") int pageNumber,
        @ApiParam(value = "Page size", example = "10") @RequestParam(defaultValue = "10") int pageSize,
        @ApiParam(value = "Sort field", example = "id") @RequestParam(defaultValue = "id") String sortBy) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
    return purchaseService.getAllPurchases(pageable);
}
    @GetMapping("/{id}")
    @ApiOperation(value = "Get purchase by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved purchase"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Purchase getPurchaseById(@PathVariable Long id) {
        return purchaseService.getPurchaseById(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a new purchase", notes = PURCHASE_POST_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added purchase"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Purchase addPurchase(@RequestBody Purchase purchase) {
        return purchaseService.savePurchase(purchase);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing purchase", notes = PURCHASE_PUT_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated purchase"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Purchase updatePurchase(@PathVariable Long id, @RequestBody Purchase purchase) {
        purchase.setId(id);
        return purchaseService.updatePurchase(purchase);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an existing purchase")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted purchase"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<?> deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.ok("Purchase deleted successfully");
    }
}
