package com.rishi.aicopilot.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntentResponse {
    private Long orderId;
    private String intent; // payment_status | delivery_status | full_summary | unknown
}
