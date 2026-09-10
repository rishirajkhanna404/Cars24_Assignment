package com.rishi.aicopilot.controller;

import com.rishi.aicopilot.dto.CopilotRequest;
import com.rishi.aicopilot.dto.CopilotResponse;
import com.rishi.aicopilot.service.CopilotService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/copilot")
public class CopilotController {

    private final CopilotService copilotService;

    @PostMapping("/query")
    public CopilotResponse query(@Valid @RequestBody CopilotRequest request) {
        String answer = copilotService.processQuery(request.getQuestion());
        return CopilotResponse.builder()
                .answer(answer)
                .build();
    }
}
