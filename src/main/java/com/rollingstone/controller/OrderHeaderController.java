package com.rollingstone.controller;


import com.rollingstone.model.OrderHeader;
import com.rollingstone.service.OrderHeaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderHeaderController {

    private final OrderHeaderService orderHeaderService;

    public OrderHeaderController(OrderHeaderService orderHeaderService) {
        this.orderHeaderService = orderHeaderService;
    }

    @GetMapping
    public List<OrderHeader> getAllOrders() {
        return orderHeaderService.findAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderHeader> getOrderById(@PathVariable Long id) {
        return orderHeaderService.findOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerNumber}")
    public List<OrderHeader> getOrdersByCustomer(@PathVariable String customerNumber) {
        return orderHeaderService.findOrdersByCustomer(customerNumber);
    }

    @GetMapping("/status/{status}")
    public List<OrderHeader> getOrdersByStatus(@PathVariable String status) {
        return orderHeaderService.findOrdersByStatus(status);
    }

    @PostMapping
    public OrderHeader createOrder(@RequestBody OrderHeader orderHeader) {
        return orderHeaderService.saveOrder(orderHeader);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderHeader> updateOrder(@PathVariable Long id, @RequestBody OrderHeader updatedOrder) {
        return orderHeaderService.findOrderById(id)
                .map(existing -> {
                    updatedOrder.setOrderId(id);
                    return ResponseEntity.ok(orderHeaderService.saveOrder(updatedOrder));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderHeaderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}

