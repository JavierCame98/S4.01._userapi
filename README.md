# S4.01.SpringBootIntroduction

This repository demonstrates the initial contact with Spring Boot and the development of a functional REST API, focusing on layered architecture, dependency injection, and comprehensive testing strategies.

## Design Pattern: Layered Architecture (Service & Repository)
The project implements a Layered Architecture to ensure a clean separation of concerns. This approach decouples HTTP handling from business logic and data persistence, making the codebase maintainable and scalable.

## Purpose

Single Responsibility: The Controller handles requests, the Service manages logic, and the Repository handles data.
Decoupling: The Service layer communicates with an interface (UserRepository), allowing the data source (In-Memory, SQL, NoSQL) to change without affecting business logic.
Inversion of Control (IoC): Leverages Spring’s Dependency Injection to manage object lifecycles and dependencies.

## Testability: 
Enables isolated unit testing of business rules using mocks to simulate data persistence.

## Exercise: User Management System
Implement a REST API to manage a collection of users. The system evolves from a simple health check to a complete CRUD-capable structure with business validation.

## Business Rules & Features
Health Monitoring: A specialized /health endpoint that returns structured JSON {"status": "OK"} to verify application uptime.
Unique Identification: Users are automatically assigned a UUID upon creation to ensure global uniqueness without a database.
Email Integrity: A business rule enforced in the Service layer prevents the creation of users with duplicate email addresses.
Dynamic Filtering: The user list supports optional filtering by name using URL query parameters (case-insensitive).
Error Handling: Returns a standard 404 Not Found response if a requested User ID does not exist in the system.

## Technical Implementation
REST Controllers: Use of @RestController and mapping annotations (@GetMapping, @PostMapping) to define the API contract.
Data Transfer: Handling JSON payloads through @RequestBody and extracting URI data with @PathVariable and @RequestParam.
Service Layer: Implementation of the Service Pattern to house validation logic (e.g., checking for existing emails).
Repository Pattern: An InMemoryUserRepository implementation that simulates a database using Java collections, marked with @Repository.

## Technologies & Testing
Backend: Java 21, Spring Boot 3.x, Maven.

Testing: JUnit 5, Mockito, and MockMvc.

## Key Concepts: 
Dependency Injection (Beans), Mocking dependencies, Integration Testing (@SpringBootTest).
The implementation is validated through three testing tiers:
Web Layer Tests: Using MockMvc to verify endpoint routing and JSON responses.

Unit Tests: Using Mockito to test the UserService logic in isolation by mocking the repository.

Integration Tests: Validating the end-to-end flow from the Controller through the Service to the Repository.
