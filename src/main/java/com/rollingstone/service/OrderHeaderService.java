package com.rollingstone.service;


import com.rollingstone.model.OrderHeader;
import com.rollingstone.repository.OrderHeaderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderHeaderService {

    private final OrderHeaderRepository orderHeaderRepository;

    public OrderHeaderService(OrderHeaderRepository orderHeaderRepository) {
        this.orderHeaderRepository = orderHeaderRepository;
    }

    public List<OrderHeader> findAllOrders() {
        return orderHeaderRepository.findAll();
    }

    public Optional<OrderHeader> findOrderById(Long id) {
        return orderHeaderRepository.findById(id);
    }

    public List<OrderHeader> findOrdersByCustomer(String customerNumber) {
        return orderHeaderRepository.findByCustomerNumber(customerNumber);
    }

    public List<OrderHeader> findOrdersByStatus(String status) {
        return orderHeaderRepository.findByOrderStatus(status);
    }

    public OrderHeader saveOrder(OrderHeader orderHeader) {
        return orderHeaderRepository.save(orderHeader);
    }

    public void deleteOrder(Long id) {
        orderHeaderRepository.deleteById(id);
    }
}
