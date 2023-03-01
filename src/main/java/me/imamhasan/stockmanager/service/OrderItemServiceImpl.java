package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.OrderItem;
import me.imamhasan.stockmanager.repository.OrderItemRepository;
import me.imamhasan.stockmanager.repository.OrderRepository;
import me.imamhasan.stockmanager.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService{
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        //  validate order id
        if(orderItem.getOrder().getId() != null){
            Long orderId = orderItem.getOrder().getId();
            orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("Order not found with id " + orderId));
        }
        //  validate product id
        if(orderItem.getProduct().getId() != null){
            Long productId = orderItem.getProduct().getId();
            productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product not found with id " + productId));
        }
        return orderItemRepository.save(orderItem);
    }

    @Override
    public Page<OrderItem> getAllOrderItems(Pageable pageable) {
        return orderItemRepository.findAll(pageable);
    }

    @Override
    public OrderItem getOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalStateException("Order with id " + orderItemId + " not found"));
    }

    @Override
    public ResponseEntity deleteOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalStateException("Order item with id " + orderItemId + " not found"));
        orderItemRepository.delete(orderItem);
        return ResponseEntity.ok().body("Order item deleted successfully.");
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        //  validate order id
        if(orderItem.getOrder().getId() != null){
            Long orderId = orderItem.getOrder().getId();
            orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("Order not found with id " + orderId));
        }
        //  validate product id
        if(orderItem.getProduct().getId() != null){
            Long productId = orderItem.getProduct().getId();
            productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product not found with id " + productId));
        }
        return orderItemRepository.save(orderItem);
    }
}
