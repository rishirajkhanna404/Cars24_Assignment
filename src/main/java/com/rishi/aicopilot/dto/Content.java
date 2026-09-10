package com.rishi.aicopilot.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Content {
    private String role;
    private List<Part> parts;
}
