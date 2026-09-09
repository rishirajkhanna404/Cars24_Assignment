package com.rishi.aicopilot.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentStatusResponse {
    private Long orderId;
    private String paymentStatus;
}
