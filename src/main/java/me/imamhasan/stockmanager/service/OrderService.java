package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Order saveOrder(Order order);
    Page<Order> getAllOrders(Pageable pageable);
    Order getOrderById(Long orderId);
    void deleteOrder(Long orderId);
    Order updateOrder(Order order);
}