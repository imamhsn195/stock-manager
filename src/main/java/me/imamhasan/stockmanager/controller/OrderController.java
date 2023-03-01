package me.imamhasan.stockmanager.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import me.imamhasan.stockmanager.model.Order;
import me.imamhasan.stockmanager.service.OrderServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.imamhasan.stockmanager.config.SwaggerPayloads.ORDER_POST_REQUEST_BODY;
import static me.imamhasan.stockmanager.config.SwaggerPayloads.ORDER_PUT_REQUEST_BODY;

@RestController
@RequestMapping("/api/orders")
@Api(value = "Order Management System", tags = { "Orders" }, description = "Operations pertaining to order in Order Management System")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;

    @GetMapping
    @ApiOperation(value = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved orders"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })

    public Page<Order> getAllOrders(
        @ApiParam(value = "Page number", example = "0") @RequestParam(defaultValue = "0") int pageNumber,
        @ApiParam(value = "Page size", example = "10") @RequestParam(defaultValue = "10") int pageSize,
        @ApiParam(value = "Sort field", example = "id") @RequestParam(defaultValue = "id") String sortBy) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
    return orderService.getAllOrders(pageable);
}
    @GetMapping("/{id}")
    @ApiOperation(value = "Get order by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved order"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a new order", notes = ORDER_POST_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added order"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Order addOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing order", notes = ORDER_PUT_REQUEST_BODY)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated order"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an existing order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted order"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully");
    }
}
