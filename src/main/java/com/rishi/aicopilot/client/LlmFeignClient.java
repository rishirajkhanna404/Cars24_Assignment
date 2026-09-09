package com.rishi.aicopilot.client;

import com.rishi.aicopilot.dto.LlmRequest;
import com.rishi.aicopilot.dto.LlmResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "llmClient", url = "${llm.api.url}")
public interface LlmFeignClient {

    @PostMapping("/v1/chat/completions")
    LlmResponse chatCompletion(@RequestBody LlmRequest request);
}
