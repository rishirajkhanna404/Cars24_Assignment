package com.rishi.aicopilot.service;

import com.rishi.aicopilot.dto.*;
import com.rishi.aicopilot.exception.OrderNotFoundException;
import com.rishi.aicopilot.model.*;
import com.rishi.aicopilot.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = Order.builder()
                .id(123L)
                .customerName("Test User")
                .status(OrderStatus.PROCESSING)
                .paymentStatus(PaymentStatus.PAID)
                .orderDate(LocalDate.now())
                .deliveryDate(null)
                .amount(new BigDecimal("100.00"))
                .build();
    }

    @Test
    void getPaymentStatus_Success() {
        when(orderRepository.findById(123L)).thenReturn(Optional.of(testOrder));

        PaymentStatusResponse response = orderService.getPaymentStatus(123L);

        assertEquals("PAID", response.getPaymentStatus());
        assertEquals(123L, response.getOrderId());
        verify(orderRepository).findById(123L);
    }

    @Test
    void getDeliveryStatus_Success() {
        when(orderRepository.findById(123L)).thenReturn(Optional.of(testOrder));

        DeliveryStatusResponse response = orderService.getDeliveryStatus(123L);

        assertEquals("PROCESSING", response.getStatus());
        assertNull(response.getDeliveryDate());
        assertEquals(123L, response.getOrderId());
    }

    @Test
    void getFullSummary_Success() {
        when(orderRepository.findById(123L)).thenReturn(Optional.of(testOrder));

        OrderSummaryResponse response = orderService.getFullSummary(123L);

        assertEquals("Test User", response.getCustomerName());
        assertEquals("PROCESSING", response.getStatus());
        assertEquals("PAID", response.getPaymentStatus());
        assertEquals(new BigDecimal("100.00"), response.getAmount());
    }

    @Test
    void getPaymentStatus_NotFound_ThrowsException() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getPaymentStatus(999L));
    }
}
