package me.imamhasan.stockmanager.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import me.imamhasan.stockmanager.model.OrderItem;
import me.imamhasan.stockmanager.service.OrderItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.imamhasan.stockmanager.config.SwaggerPayloads.*;

@RestController
@RequestMapping("/api/order_items")
@Api(value = "Order Item Management System", tags = { "Order Items" }, description = "Operations pertaining to order item in Order Item Management System")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping
    @ApiOperation(value = "Get all order items")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved order items"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })

    public Page<OrderItem> getAllOrderItems(
        @ApiParam(value = "Page number", example = "0") @RequestParam(defaultValue = "0") int pageNumber,
        @ApiParam(value = "Page size", example = "10") @RequestParam(defaultValue = "10") int pageSize,
        @ApiParam(value = "Sort field", example = "id") @RequestParam(defaultValue = "id") String sortBy) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
    return orderItemService.getAllOrderItems(pageable);
}
    @GetMapping("/{id}")
    @ApiOperation(value = "Get orderItem by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved order item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public OrderItem getOrderById(@PathVariable Long id) {
        return orderItemService.getOrderItemById(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a new order item", notes = ORDER_ITEM_POST_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added order item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public OrderItem addOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.saveOrderItem(orderItem);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing order item", notes = ORDER_ITEM_PUT_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated order item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public OrderItem updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        orderItem.setId(id);
        return orderItemService.updateOrderItem(orderItem);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an existing order item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted order item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<?> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok("Order deleted successfully");
    }
}
