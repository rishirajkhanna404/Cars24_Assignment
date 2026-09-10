package com.rishi.aicopilot.client;

import com.rishi.aicopilot.dto.LlmRequest;
import com.rishi.aicopilot.dto.LlmResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "llmClient", url = "${llm.api.url}")
public interface LlmFeignClient {

    @GetMapping("/v1beta/models")
    String listModels(@RequestParam("key") String apiKey);

    @PostMapping("/v1beta/models/{model}:generateContent")
    LlmResponse chatCompletion(
            @PathVariable("model") String model,
            @RequestBody LlmRequest request,
            @RequestParam("key") String apiKey
    );
}
