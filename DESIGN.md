# System Design: AI Operations Copilot

## Architecture Overview
The AI Operations Copilot is designed as a decoupled, three-stage pipeline to ensure that the final answers are grounded in real-time database facts, eliminating the risk of LLM hallucinations.

### The 3-Step Pipeline
1. **Intent Extraction (LLM)**: 
   - The user's natural language query is sent to the LLM with a strict system prompt.
   - The LLM extracts two pieces of information: the `orderId` and the `intent` (e.g., `payment_status`, `delivery_status`, `full_summary`).
   - The output is constrained to a JSON format for deterministic parsing by the Java backend.

2. **Deterministic Data Retrieval (Java/Spring Data JPA)**:
   - The backend uses the extracted `orderId` to query the H2 database.
   - Depending on the `intent`, the `OrderService` retrieves specific fields or a full object summary.
   - This step ensures that the "truth" comes from the database, not the LLM's training data.

3. **Natural Language Generation (LLM)**:
   - The retrieved database record is passed back to the LLM as context.
   - The LLM is instructed to act as a professional operations copilot and phrase the answer based *only* on the provided data.
   - This results in a grounded, polite, and accurate response.

## Key Technical Decisions

### 1. Two-Step LLM Orchestration vs. Text-to-SQL
- **Choice**: Two-step intent extraction $\rightarrow$ Java lookup.
- **Trade-off**: Text-to-SQL can be powerful but is prone to security risks (SQL injection) and hallucinations (inventing tables or columns). By using a deterministic Java service for retrieval, we maintain absolute control over data access and security.

### 2. Resilience4j Circuit Breaker
- **Choice**: Wrapping LLM calls in a Resilience4j Circuit Breaker.
- **Reason**: External LLM APIs can be unstable, hit rate limits, or suffer from latency. The circuit breaker prevents the application from hanging and provides a graceful fallback response when the AI "brain" is unavailable.

### 3. Spring Cloud OpenFeign
- **Choice**: Using Feign for API communication with Google Gemini.
- **Reason**: Feign provides a declarative way to define REST clients, reducing boilerplate code and making the integration with the Gemini native endpoint cleaner and easier to maintain.

### 4. H2 In-Memory Database
- **Choice**: H2 for seed data.
- **Reason**: Ensures the project is "plug-and-play" for the reviewer without requiring an external database setup, while still demonstrating JPA and repository patterns.

## Data Flow Diagram
`User Query` $\rightarrow$ `CopilotController` $\rightarrow$ `CopilotService` $\rightarrow$ `LlmFeignClient (Intent Extraction)` $\rightarrow$ `OrderRepository (Data Lookup)` $\rightarrow$ `LlmFeignClient (Final Phrasing)` $\rightarrow$ `User Answer`

## Error Handling
- **Missing Order ID**: If the LLM cannot find an order ID, the system asks the user to provide one.
- **Order Not Found**: If the ID is found but doesn't exist in the database, a specific "Not Found" message is returned.
- **API Failure**: Circuit breaker fallback is triggered on 4xx/5xx errors or timeouts.
