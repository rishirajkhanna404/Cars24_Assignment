package com.rishi.aicopilot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LlmRequest {
    private List<Content> contents;

    @JsonProperty("system_instruction")
    private SystemInstruction systemInstruction;
}
