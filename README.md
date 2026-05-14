# Customer Rewards API

This project is a Spring Boot REST API application that calculates reward points for customers based on their transactions.

---

# Problem Statement

A retailer gives reward points to customers based on the amount spent on each transaction.

## Reward Rules

- 2 points for every dollar spent above $100
- 1 point for every dollar spent between $50 and $100

### Example

If transaction amount is `$120`

- Amount between $50 and $100 → 50 points
- Amount above $100 → 20 × 2 = 40 points

Total points = 90

---

# Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven
- Swagger UI

---

# Project Structure

```text
src/main/java/com/rewards
│
├── controller
├── service
├── repository
├── model
└── CustomerRewardsApplication.java
```

---

# Layers Used

## Controller Layer

Handles API requests.

File:
```text
RewardController.java
```

Endpoint:
```http
GET /api/rewards
```

---

## Service Layer

Contains business logic for calculating reward points.

File:
```text
RewardService.java
```

---

## Repository Layer

Used for database operations with JPA.

File:
```text
TransactionRepository.java
```

---

## Model Layer

Contains entity and response classes.

Files:
```text
Transaction.java
RewardResponse.java
```

---

# Database

H2 in-memory database is used for this project.

Sample transaction data is inserted using:

```text
src/main/resources/data.sql
```

---

# Reward Calculation Logic

## Case 1

Amount <= 50

```text
0 points
```

---

## Case 2

Amount between 51 and 100

```text
amount - 50
```

Example:

```text
75 - 50 = 25 points
```

---

## Case 3

Amount > 100

```text
50 points +
(amount - 100) * 2
```

Example:

```text
120

50 + (20 * 2)
= 90 points
```

---

# API Endpoint

## Get Rewards

```http
GET /api/rewards
```

---

# Sample Response

```json
[
  {
    "customerId": 101,
    "customerName": "John",
    "monthlyRewards": {
      "JANUARY": 115,
      "FEBRUARY": 70,
      "MARCH": 45
    },
    "totalRewards": 230
  },
  {
    "customerId": 102,
    "customerName": "Alice",
    "monthlyRewards": {
      "JANUARY": 0,
      "FEBRUARY": 250,
      "MARCH": 350
    },
    "totalRewards": 600
  }
]
```

---

# How to Run the Project

## Clone Project

```bash
git clone <github-url>
```

---

## Build Project

```bash
mvn clean install
```

---

## Run Application

```bash
mvn spring-boot:run
```

---

# URLs

## API

```text
http://localhost:8080/api/rewards
```

## Swagger UI

```text
http://localhost:8080/swagger-ui.html
```

## H2 Console

```text
http://localhost:8080/h2-console
```

---

# H2 Database Credentials

| Property | Value |
|---|---|
| JDBC URL | jdbc:h2:mem:rewardsdb |
| Username | sa |
| Password | |

---

# Important Configurations

```properties
spring.datasource.url=jdbc:h2:mem:rewardsdb

spring.jpa.hibernate.ddl-auto=create

spring.jpa.defer-datasource-initialization=true
```

---

# Features

- REST API using Spring Boot
- Reward points calculation
- Monthly and total rewards
- H2 database integration
- Swagger documentation
- Clean layered structure

---

# Test Cases

The project includes unit test cases for:

- Reward calculation for amount less than 50
- Reward calculation for amount between 50 and 100
- Reward calculation for amount greater than 100
- Multiple customer reward calculation
- No transaction scenario
- Controller API testing

---

# Author
Vishnu Gopal