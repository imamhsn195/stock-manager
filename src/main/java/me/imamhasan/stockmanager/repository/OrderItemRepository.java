package me.imamhasan.stockmanager.repository;

import me.imamhasan.stockmanager.model.Order;
import me.imamhasan.stockmanager.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
