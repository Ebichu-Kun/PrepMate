# PrepMate Backend

PrepMate Backend is a Spring Boot REST API that powers **PrepMate**, a platform for tracking DSA practice, taking AI-powered mock interviews, and managing study notes.

The application uses JWT authentication to keep user data secure and integrates with a locally running Ollama model to generate interview questions and evaluate responses.

---

## Features

- User registration and login with JWT authentication
- Track DSA problems with difficulty, status, notes, and platform details
- Create, update, search, and manage study notes
- Generate AI-powered mock interviews
- Receive AI-based interview evaluation and feedback
- User-specific data isolation
- Consistent API response format

---

## Tech Stack

- Java 21
- Spring Boot
  - Spring Web
  - Spring Security
  - Spring Data JPA
  - Validation
  - WebFlux
- PostgreSQL
- JWT Authentication
- Ollama

---

## Prerequisites

Before running the project, make sure you have:

- Java 21 installed
- PostgreSQL running locally with a database named `prepmate`
- Ollama installed and running locally
- An Ollama model downloaded (for example, `gemma3:4b`)

---

## Configuration

Application configuration is located in:

```text
backend/src/main/resources/application.properties
```

Configure the following properties before starting the application.

| Property | Description |
|----------|-------------|
| `spring.datasource.url` | PostgreSQL database URL |
| `spring.datasource.username` | Database username |
| `spring.datasource.password` | Database password |
| `jwt.secret` | Secret key used for signing JWTs |
| `jwt.expiration` | JWT expiration time (milliseconds) |
| `ollama.url` | Ollama `/api/generate` endpoint |
| `ollama.model` | Ollama model name |

> Make sure your database is created and Ollama is running with the configured model before starting the application.

---

## Running the Application

```bash
cd backend
./mvnw spring-boot:run
```

The application will start on:

```
http://localhost:8080
```

---

## Authentication

Only the following endpoints are publicly accessible:

- `/api/auth/register`
- `/api/auth/login`

All other endpoints require a JWT token.

Include the token in the `Authorization` header:

```http
Authorization: Bearer <token>
```

The JWT token is returned after a successful login.

Each authenticated user can only access their own DSA problems, notes, and interview sessions.

---

## Response Format

All endpoints return responses in the following format:

```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": {}
}
```

For validation errors, the response has HTTP status `400`, and `data` contains a map of validation errors.

---

# API Endpoints

## Authentication

Base URL: `/api/auth`

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/register` | Register a new user |
| POST | `/login` | Login and receive a JWT |

---

## DSA Problems

Base URL: `/api/dsa`

Manage your DSA practice by tracking problems, progress, and notes.

| Method | Endpoint |
|---------|----------|
| POST | `/` |
| GET | `/` |
| GET | `/{id}` |
| PUT | `/{id}` |
| DELETE | `/{id}` |
| GET | `/status/{status}` |
| GET | `/difficulty/{difficulty}` |
| GET | `/search/title?keyword=` |
| GET | `/search/topic?keyword=` |

Supported Status:

- `NOT_STARTED`
- `IN_PROGRESS`
- `SOLVED`
- `REVISION`

Supported Difficulty:

- `EASY`
- `MEDIUM`
- `HARD`

---

## Interview Sessions

Base URL: `/api/interviews`

Create mock interviews, answer AI-generated questions, and receive detailed evaluations.

| Method | Endpoint |
|---------|----------|
| POST | `/` |
| POST | `/start` |
| GET | `/` |
| GET | `/{id}` |
| DELETE | `/{id}` |
| POST | `/{sessionId}/answer` |
| POST | `/{sessionId}/finish` |

---

## Notes

Base URL: `/api/notes`

Create and organize study notes.

| Method | Endpoint |
|---------|----------|
| POST | `/` |
| GET | `/` |
| GET | `/{id}` |
| PUT | `/{id}` |
| DELETE | `/{id}` |
| GET | `/search?title=` |

---

## AI Test

Base URL: `/api/test-ai`

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/test-ai?prompt=` | Sends a prompt directly to Ollama and returns the raw response. Useful for verifying that the AI integration is working correctly. |

---

## Project Structure

```text
backend/src/main/java/com/strawHat/backend/
├── controller/      REST controllers
├── service/         Business logic interfaces
├── service/impl/    Service implementations
├── service/ai/      AI provider abstraction
├── repository/      Spring Data JPA repositories
├── entity/          JPA entities
├── dto/             Request and response DTOs
├── enums/           Application enums
├── security/        JWT authentication and Spring Security configuration
├── exception/       Global exception handling
└── config/          Configuration classes
```
