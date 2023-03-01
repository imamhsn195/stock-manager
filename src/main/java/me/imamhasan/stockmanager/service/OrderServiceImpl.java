package me.imamhasan.stockmanager.service;

import lombok.AllArgsConstructor;
import me.imamhasan.stockmanager.model.Address;
import me.imamhasan.stockmanager.model.Customer;
import me.imamhasan.stockmanager.model.Order;
import me.imamhasan.stockmanager.repository.AddressRepository;
import me.imamhasan.stockmanager.repository.CustomerRepository;
import me.imamhasan.stockmanager.repository.OrderRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;


    @Override
    public Order saveOrder(@NotNull Order order) {
//        save the order
        if(order.getCustomer().getId() != null){
            Long customerId = order.getCustomer().getId();
            customerRepository.findById(customerId).orElseThrow(() -> new IllegalStateException("Customer not found with id " + customerId));
        }else{
            if (order.getCustomer().getAddress().getId() != null){
                addressRepository.findById(order.getCustomer().getAddress().getId())
                        .orElseThrow(() -> new IllegalStateException("Address not found with id " + order.getCustomer().getAddress().getId()));
            }else{
                Address address = addressRepository.save(order.getCustomer().getAddress());
                order.getCustomer().setAddress(address);
            }
            Customer customer = customerRepository.save(order.getCustomer());
            order.setCustomer(customer);
        }
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order with id " + orderId + " not found"));
    }

    @Override
    public ResponseEntity deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order with id " + orderId + " not found"));
        orderRepository.delete(order);
        return ResponseEntity.ok().body("Order deleted successfully.");
    }

    @Override
    public Order updateOrder(@NotNull Order order) {
//        find the order
        orderRepository.findById(order.getId())
                .orElseThrow(() -> new IllegalStateException("Order with id " + order.getId() + " not found"));
//        save the order
        if(order.getCustomer().getId() != null){
            Long customerId = order.getCustomer().getId();
            customerRepository.findById(customerId).orElseThrow(() -> new IllegalStateException("Customer not found with id " + customerId));
        }else{
            if (order.getCustomer().getAddress().getId() != null){
                addressRepository.findById(order.getCustomer().getAddress().getId())
                        .orElseThrow(() -> new IllegalStateException("Address not found with id " + order.getCustomer().getAddress().getId()));
            }else{
                Address address = addressRepository.save(order.getCustomer().getAddress());
                order.getCustomer().setAddress(address);
            }
            Customer customer = customerRepository.save(order.getCustomer());
            order.setCustomer(customer);
        }
        return orderRepository.save(order);
    }
}
