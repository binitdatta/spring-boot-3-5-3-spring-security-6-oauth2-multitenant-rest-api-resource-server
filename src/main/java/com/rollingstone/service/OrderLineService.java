package com.rollingstone.service;


import com.rollingstone.model.OrderLine;
import com.rollingstone.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    public List<OrderLine> findAllLines() {
        return orderLineRepository.findAll();
    }

    public Optional<OrderLine> findLineById(Long id) {
        return orderLineRepository.findById(id);
    }

    public List<OrderLine> findLinesByOrderId(Long orderId) {
        return orderLineRepository.findByOrderHeaderOrderId(orderId);
    }

    public OrderLine saveLine(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    public void deleteLine(Long id) {
        orderLineRepository.deleteById(id);
    }
}

