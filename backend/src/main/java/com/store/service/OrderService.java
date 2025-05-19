package com.store.service;

import com.store.model.Order;
import com.store.model.OrderItem;
import com.store.model.Product;
import com.store.model.User;
import com.store.model.OrderStatus;
import com.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("unused")
@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductService productService;
    
    @Transactional
    public Order createOrder(Order order) {
        // Verificar e atualizar estoque
        for (OrderItem item : order.getItems()) {
            productService.updateStock(item.getProduct().getId(), item.getQuantity());
        }
        
        // Calcular valor total
        order.calculateTotalAmount();
        
        // Definir status inicial
        order.setStatus(OrderStatus.PENDING);
        
        return orderRepository.save(order);
    }
    
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }
    
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }
    
    public Page<Order> findByUserPaged(User user, Pageable pageable) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user, pageable);
    }
    
    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = findById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
    
    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }
    
    public List<Order> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByDateRange(startDate, endDate);
    }
    
    public Long countByStatus(OrderStatus status) {
        return orderRepository.countByStatus(status);
    }
    
    public List<Order> findByUserAndStatus(User user, OrderStatus status) {
        return orderRepository.findByUserAndStatus(user, status);
    }
} 