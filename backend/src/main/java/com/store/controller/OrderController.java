package com.store.controller;

import com.store.model.Order;
import com.store.model.User;
import com.store.model.OrderStatus;
import com.store.service.OrderService;
import com.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody Order order,
            @AuthenticationPrincipal User user) {
        order.setUser(user);
        return ResponseEntity.ok(orderService.createOrder(order));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Order>> getUserOrders(
            @PathVariable Long userId,
            Pageable pageable) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return ResponseEntity.ok(orderService.findByUserPaged(user, pageable));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(
            @PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.findByStatus(status));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Order>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(orderService.findByDateRange(startDate, endDate));
    }
    
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> countOrdersByStatus(
            @PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.countByStatus(status));
    }
    
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Order>> getUserOrdersByStatus(
            @PathVariable Long userId,
            @PathVariable OrderStatus status) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return ResponseEntity.ok(orderService.findByUserAndStatus(user, status));
    }
} 