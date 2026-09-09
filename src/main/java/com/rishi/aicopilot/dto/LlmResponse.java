package com.rishi.aicopilot.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LlmResponse {
    private List<Choice> choices;
}
