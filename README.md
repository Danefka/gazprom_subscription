# Subscription Service

REST API for managing user subscriptions to services and tariffs.

The project implements a complete subscription lifecycle with authentication, authorization, filtering, pagination, status transitions and automatic expiration handling.

Built as a production-style backend application using Spring Boot, Spring Security, JWT authentication, PostgreSQL, Liquibase and integration testing with Testcontainers.

## Tech stack

- Kotlin
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Liquibase
- OpenAPI / Swagger
- JUnit
- Testcontainers
- Docker Compose


## Features

### Security

- Stateless JWT authentication and authorization
- Role-based access control (USER / ADMIN)

### Core functionality

- Subscription lifecycle management
- Business validation for status transitions
- Subscription status history tracking
- Scheduler for automatic subscription expiration

### API and data access

- RESTful API
- Filtering by user, service, status and date range
- Pagination and sorting

### Reliability and error handling

- Global exception handling

### Documentation

- OpenAPI / Swagger documentation

### Infrastructure

- Database migrations with Liquibase
- Docker Compose environment setup

### Testing

- Integration testing with Testcontainers



## Run locally

```bash
./gradlew clean bootJar
docker compose up --build
```

## Run tests

```bash
./gradlew test
```

If Testcontainers Ryuk cannot start locally:

```bash
TESTCONTAINERS_RYUK_DISABLED=true ./gradlew test
```

## API documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI specification:

```text
http://localhost:8080/v3/api-docs
```

## Main endpoints

### Authentication

```text
POST /auth/register
POST /auth/login
```

### User subscriptions

```text
POST   /subscriptions
GET    /subscriptions/{id}
GET    /subscriptions/my
GET    /subscriptions/my/active

PATCH  /subscriptions/{id}/pause
PATCH  /subscriptions/{id}/resume
PATCH  /subscriptions/{id}/cancel
```

### Admin subscriptions

```text
GET    /admin/subscriptions
GET    /admin/subscriptions/{id}

PATCH  /admin/subscriptions/{id}/status
```

## Filtering, pagination and sorting

Example:

```text
GET /admin/subscriptions?userId=3fa85f64-5717-4562-b3fc-2c963f66afa6&serviceName=Netflix&status=ACTIVE&page=0&size=10&sort=endDate,asc
```

Available filters:

```text
userId
serviceName
status
startDateFrom
startDateTo
endDateFrom
endDateTo
```

Pagination:

```text
page=0
size=10
```

Sorting:

```text
sort=endDate,asc
sort=price,desc
```

