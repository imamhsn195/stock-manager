package me.imamhasan.stockmanager.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import me.imamhasan.stockmanager.model.Sale;
import me.imamhasan.stockmanager.service.SaleServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.imamhasan.stockmanager.config.SwaggerPayloads.ORDER_POST_REQUEST_BODY;
import static me.imamhasan.stockmanager.config.SwaggerPayloads.ORDER_PUT_REQUEST_BODY;

@RestController
@RequestMapping("/api/sales")
@Api(value = "Sale Management System", tags = { "Sales" }, description = "Operations pertaining to sale in Sale Management System")
@RequiredArgsConstructor
public class SaleController {
    private final SaleServiceImpl saleService;

    @GetMapping
    @ApiOperation(value = "Get all sales")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sales"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })

    public Page<Sale> getAllSales(
        @ApiParam(value = "Page number", example = "0") @RequestParam(defaultValue = "0") int pageNumber,
        @ApiParam(value = "Page size", example = "10") @RequestParam(defaultValue = "10") int pageSize,
        @ApiParam(value = "Sort field", example = "id") @RequestParam(defaultValue = "id") String sortBy) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
    return saleService.getAllSales(pageable);
}
    @GetMapping("/{id}")
    @ApiOperation(value = "Get sale by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sale"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Sale getSaleById(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a new sale", notes = ORDER_POST_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added sale"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Sale addSale(@RequestBody Sale sale) {
        return saleService.saveSale(sale);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing sale", notes = ORDER_PUT_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated sale"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Sale updateSale(@PathVariable Long id, @RequestBody Sale sale) {
        sale.setId(id);
        return saleService.updateSale(sale);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an existing sale")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted sale"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<?> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.ok("Sale deleted successfully");
    }
}
