# BankCore

BankCore is a backend banking and transaction management system built with Java and Spring Boot.

## Overview

This project provides a clean, interview-friendly banking backend with account operations, customer management, and transaction processing. It is intentionally kept understandable for portfolio use and discussion in Java backend interviews.

## Tech Stack

- Java 21
- Spring Boot 3.4.1
- Spring Data JPA / Hibernate
- H2 for local development defaults
- MySQL-ready configuration via environment variables
- Spring Security with BCrypt password hashing

## Local configuration

Set database and security values through environment variables when running locally:

```bash
export DATABASE_URL=jdbc:h2:mem:bankcore;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
export DATABASE_USERNAME=sa
export DATABASE_PASSWORD=password
export BANKCORE_ADMIN_USERNAME=bankcore-admin
export BANKCORE_ADMIN_PASSWORD=change-me-in-env
```

## Run

```bash
./mvnw clean test
./mvnw spring-boot:run
```

## Notes

- Sensitive credentials are not hardcoded in source files.
- Monetary values are tracked using BigDecimal instead of floating-point types.
- The project retains the original banking domain while being refactored into a clearer BankCore structure.

     
