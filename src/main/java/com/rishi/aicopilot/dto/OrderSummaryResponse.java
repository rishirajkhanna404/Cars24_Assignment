package com.rishi.aicopilot.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSummaryResponse {
    private Long orderId;
    private String customerName;
    private String status;
    private String paymentStatus;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private BigDecimal amount;
}
