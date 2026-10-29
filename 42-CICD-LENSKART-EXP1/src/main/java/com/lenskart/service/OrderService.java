package com.lenskart.service;

import com.lenskart.dto.OrderRequest;
import com.lenskart.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest request);
    OrderResponse getById(Long id);
    List<OrderResponse> getAll();
    OrderResponse updateStatus(Long id, String status);
    void delete(Long id);
}