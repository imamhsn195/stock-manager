package me.imamhasan.stockmanager.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import me.imamhasan.stockmanager.model.SaleItem;
import me.imamhasan.stockmanager.service.SaleItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.imamhasan.stockmanager.config.SwaggerPayloads.*;

@RestController
@RequestMapping("/api/sale_items")
@Api(value = "Sale Item Management System", tags = { "Sale Items" }, description = "Operations pertaining to sale item in Sale Item Management System")
@RequiredArgsConstructor
public class SaleItemController {
    private final SaleItemService saleItemService;

    @GetMapping
    @ApiOperation(value = "Get all sale items")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sale items"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })

    public Page<SaleItem> getAllSaleItems(
        @ApiParam(value = "Page number", example = "0") @RequestParam(defaultValue = "0") int pageNumber,
        @ApiParam(value = "Page size", example = "10") @RequestParam(defaultValue = "10") int pageSize,
        @ApiParam(value = "Sort field", example = "id") @RequestParam(defaultValue = "id") String sortBy) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
    return saleItemService.getAllSaleItems(pageable);
}
    @GetMapping("/{id}")
    @ApiOperation(value = "Get saleItem by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sale item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public SaleItem getSaleById(@PathVariable Long id) {
        return saleItemService.getSaleItemById(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a new sale item", notes = ORDER_ITEM_POST_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added sale item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public SaleItem addSaleItem(@RequestBody SaleItem saleItem) {
        return saleItemService.saveSaleItem(saleItem);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing sale item", notes = ORDER_ITEM_PUT_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated sale item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public SaleItem updateSaleItem(@PathVariable Long id, @RequestBody SaleItem saleItem) {
        saleItem.setId(id);
        return saleItemService.updateSaleItem(saleItem);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an existing sale item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted sale item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<?> deleteSaleItem(@PathVariable Long id) {
        saleItemService.deleteSaleItem(id);
        return ResponseEntity.ok("Sale deleted successfully");
    }
}
