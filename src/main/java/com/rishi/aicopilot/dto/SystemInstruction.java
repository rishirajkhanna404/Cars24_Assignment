package com.rishi.aicopilot.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemInstruction {
    private List<Part> parts;
}
