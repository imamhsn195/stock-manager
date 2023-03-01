package me.imamhasan.stockmanager.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import me.imamhasan.stockmanager.model.PurchaseItem;
import me.imamhasan.stockmanager.service.PurchaseItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.imamhasan.stockmanager.config.SwaggerPayloads.PURCHASE_ITEM_POST_REQUEST_BODY;
import static me.imamhasan.stockmanager.config.SwaggerPayloads.PURCHASE_ITEM_PUT_REQUEST_BODY;

@RestController
@RequestMapping("/api/purchase_items")
@Api(value = "Purchase Item Management System", tags = { "Purchase Items" }, description = "Operations pertaining to purchase item in Purchase Item Management System")
@RequiredArgsConstructor
public class PurchaseItemController {
    private final PurchaseItemService purchaseItemService;

    @GetMapping
    @ApiOperation(value = "Get all purchase items")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved purchase items"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })

    public Page<PurchaseItem> getAllPurchaseItems(
        @ApiParam(value = "Page number", example = "0") @RequestParam(defaultValue = "0") int pageNumber,
        @ApiParam(value = "Page size", example = "10") @RequestParam(defaultValue = "10") int pageSize,
        @ApiParam(value = "Sort field", example = "id") @RequestParam(defaultValue = "id") String sortBy) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
    return purchaseItemService.getAllPurchaseItems(pageable);
}
    @GetMapping("/{id}")
    @ApiOperation(value = "Get purchaseItem by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved purchase item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public PurchaseItem getPurchaseById(@PathVariable Long id) {
        return purchaseItemService.getPurchaseItemById(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a new purchase item", notes = PURCHASE_ITEM_POST_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added purchase item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public PurchaseItem addPurchaseItem(@RequestBody PurchaseItem purchaseItem) {
        return purchaseItemService.savePurchaseItem(purchaseItem);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing purchase item", notes = PURCHASE_ITEM_PUT_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated purchase item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public PurchaseItem updatePurchaseItem(@PathVariable Long id, @RequestBody PurchaseItem purchaseItem) {
        purchaseItem.setId(id);
        return purchaseItemService.updatePurchaseItem(purchaseItem);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an existing purchase item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted purchase item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<?> deletePurchaseItem(@PathVariable Long id) {
        purchaseItemService.deletePurchaseItem(id);
        return ResponseEntity.ok("Purchase deleted successfully");
    }
}
