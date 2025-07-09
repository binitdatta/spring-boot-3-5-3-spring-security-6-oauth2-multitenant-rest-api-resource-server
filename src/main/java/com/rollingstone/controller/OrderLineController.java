package com.rollingstone.controller;


import com.rollingstone.model.OrderLine;
import com.rollingstone.service.OrderLineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-lines")
public class OrderLineController {

    private final OrderLineService orderLineService;

    public OrderLineController(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;
    }

    @GetMapping
    public List<OrderLine> getAllLines() {
        return orderLineService.findAllLines();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderLine> getLineById(@PathVariable Long id) {
        return orderLineService.findLineById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    public List<OrderLine> getLinesByOrderId(@PathVariable Long orderId) {
        return orderLineService.findLinesByOrderId(orderId);
    }

    @PostMapping
    public OrderLine createLine(@RequestBody OrderLine orderLine) {
        return orderLineService.saveLine(orderLine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderLine> updateLine(@PathVariable Long id, @RequestBody OrderLine updatedLine) {
        return orderLineService.findLineById(id)
                .map(existing -> {
                    updatedLine.setOrderLineId(id);
                    return ResponseEntity.ok(orderLineService.saveLine(updatedLine));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLine(@PathVariable Long id) {
        orderLineService.deleteLine(id);
        return ResponseEntity.noContent().build();
    }
}
