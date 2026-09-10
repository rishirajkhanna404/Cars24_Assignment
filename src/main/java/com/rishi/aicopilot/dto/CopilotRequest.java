package com.rishi.aicopilot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CopilotRequest {
    @NotBlank(message = "Question cannot be blank")
    private String question;
}
