package com.lenskart.service;

import com.lenskart.dto.OrderRequest;
import com.lenskart.dto.OrderResponse;
import com.lenskart.entity.Order;
import com.lenskart.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
    OrderRepository orderRepository;

    @Override
    public OrderResponse create(OrderRequest request) {
        Order order = new Order();
        order.setOrderNumber("ORD" + System.currentTimeMillis());
        order.setCustomerName(request.getCustomerName());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setShippingAddress(request.getShippingAddress());
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());

        return toResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        return toResponse(order);
    }

    @Override
    public List<OrderResponse> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        order.setStatus(status);
        return toResponse(orderRepository.save(order));
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    private OrderResponse toResponse(Order order) {
        OrderResponse res = new OrderResponse();
        res.setId(order.getId());
        res.setOrderNumber(order.getOrderNumber());
        res.setCustomerName(order.getCustomerName());
        res.setEmail(order.getEmail());
        res.setPhone(order.getPhone());
        res.setShippingAddress(order.getShippingAddress());
        res.setStatus(order.getStatus());
        res.setTotalAmount(order.getTotalAmount());
        res.setCreatedAt(order.getCreatedAt());
        return res;
    }
}