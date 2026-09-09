package com.rishi.aicopilot.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LlmRequest {
    private String model;
    private List<Message> messages;
    private String response_format; // To request JSON mode
}
