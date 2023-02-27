package me.imamhasan.stockmanager.repository;

import me.imamhasan.stockmanager.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
