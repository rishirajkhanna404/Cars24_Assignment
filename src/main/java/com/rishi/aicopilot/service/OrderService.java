package com.rishi.aicopilot.service;

import com.rishi.aicopilot.dto.*;
import com.rishi.aicopilot.exception.OrderNotFoundException;
import com.rishi.aicopilot.model.Order;
import com.rishi.aicopilot.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    public PaymentStatusResponse getPaymentStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        return PaymentStatusResponse.builder()
                .orderId(order.getId())
                .paymentStatus(order.getPaymentStatus().name())
                .build();
    }

    public DeliveryStatusResponse getDeliveryStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        return DeliveryStatusResponse.builder()
                .orderId(order.getId())
                .status(order.getStatus().name())
                .deliveryDate(order.getDeliveryDate())
                .build();
    }

    public OrderSummaryResponse getFullSummary(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        return OrderSummaryResponse.builder()
                .orderId(order.getId())
                .customerName(order.getCustomerName())
                .status(order.getStatus().name())
                .paymentStatus(order.getPaymentStatus().name())
                .orderDate(order.getOrderDate())
                .deliveryDate(order.getDeliveryDate())
                .amount(order.getAmount())
                .build();
    }
}
