package com.rishi.aicopilot.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryStatusResponse {
    private Long orderId;
    private String status;
    private LocalDate deliveryDate;
}
