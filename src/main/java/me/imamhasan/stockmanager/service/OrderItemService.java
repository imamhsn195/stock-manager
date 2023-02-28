package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Order;
import me.imamhasan.stockmanager.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {
    OrderItem saveOrderItem(OrderItem orderItem);
    Page<OrderItem> getAllOrderItems(Pageable pageable);
    OrderItem getOrderItemById(Long orderItemId);
    void deleteOrderItem(Long orderItemId);
    OrderItem updateOrderItem(OrderItem orderItem);
}