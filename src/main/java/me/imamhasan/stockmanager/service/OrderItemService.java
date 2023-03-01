package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Order;
import me.imamhasan.stockmanager.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface OrderItemService {
    OrderItem saveOrderItem(OrderItem orderItem);
    Page<OrderItem> getAllOrderItems(Pageable pageable);
    OrderItem getOrderItemById(Long orderItemId);
    ResponseEntity deleteOrderItem(Long orderItemId);
    OrderItem updateOrderItem(OrderItem orderItem);
}