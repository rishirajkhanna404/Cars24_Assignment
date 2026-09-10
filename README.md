# AI Operations Copilot - Cars24 Backend Assignment

**Live Working video:** https://youtu.be/1zzSSjTDEm4
**Download video:** https://github.com/rishirajkhanna404/Cars24_Assignment_Rishi_Raj_Khanna/raw/main/assets/demo.mp4


An AI-powered backend service designed to help operations teams answer customer and operational queries about orders using natural language.

## Features
- **Natural Language Interface**: Take user queries in plain English.
- **Intent Extraction**: Uses Google Gemini LLM to extract the Order ID and the specific intent (Payment Status, Delivery Status, or Full Summary).
- **Deterministic Data Retrieval**: Fetches grounded data from an H2 in-memory database based on the extracted intent.
- **Grounded Answer Generation**: Uses the LLM to phrase a professional, natural language response based solely on the retrieved data.
- **Fault Tolerance**: Integrated Resilience4j Circuit Breaker to handle LLM API instability.

## Tech Stack
- **Language**: Java 21
- **Framework**: Spring Boot 4.1.1
- **Database**: H2 (In-Memory)
- **LLM**: Google Gemini API (`gemini-3.6-flash`)
- **Client**: Spring Cloud OpenFeign
- **Resilience**: Resilience4j

## Setup and Installation

### Prerequisites
- Java 21 installed
- Maven installed
- A Google Gemini API Key

### Running the Application
1. **Run the Application**:
   Run the application by providing the Gemini API key as an environment variable:
   ```bash
   GEMINI_API_KEY=your_api_key_here ./mvnw spring-boot:run
   ```
   The server will start on `http://localhost:8080`.

3. **H2 Console**:
   You can access the in-memory database via the H2 console:
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:testdb`
   - User: `sa`
   - Password: (empty)

## API Documentation

### Query Copilot
Processes a natural language query about an order.

- **Endpoint**: `POST /copilot/query`
- **Content-Type**: `application/json`

**Request Body**:
```json
{
  "question": "What is the payment status of order 4521?"
}
```

**Example Responses**:
- **Success**:
  ```json
  {
    "answer": "The payment for order #4521 has been successfully processed and is currently marked as PAID."
  }
  ```
- **Order Not Found**:
  ```json
  {
    "answer": "I found the request for order #4521, but unfortunately, that order does not exist in our system."
  }
  ```
- **Insufficient Information**:
  ```json
  {
    "answer": "I'm sorry, I couldn't identify the order ID or the specific information you're looking for. Could you please provide the order number?"
  }
  ```

## Example Test Queries
You can verify the service using the following `curl` commands:

1. **Payment Status**:
   ```bash
   curl -X POST http://localhost:8080/copilot/query -H "Content-Type: application/json" -d '{"question": "What is the payment status of order 4521?"}'
   ```

2. **Delivery Status**:
   ```bash
   curl -X POST http://localhost:8080/copilot/query -H "Content-Type: application/json" -d '{"question": "When will order 1289 be delivered?"}'
   ```

3. **Full Summary**:
   ```bash
   curl -X POST http://localhost:8080/copilot/query -H "Content-Type: application/json" -d '{"question": "Give me a full status summary for order 2231"}'
   ```
