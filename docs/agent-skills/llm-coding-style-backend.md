# Backend coding style and core business reminders

## Purpose

Keep backend code small, readable, and easy to verify.

Use this file for Spring Boot-only slices: controllers, services, domain, persistence, validation, and API tests. If the change also touches Angular, load the fullstack style doc as well.

This repo is a Spring Boot banking backend. The controller layer is only the delivery shell; the wallet and money-movement rules are the core business system and must stay explicit in the code.

When coding, always ask: what business rule am I protecting, what can fail, and how do I prove it with tests?

## Project mental model

- The core business system is wallet creation, balance changes, and money transfer.
- MVC is the presentation and request-handling shell.
- Business rules belong in services and domain objects, not in controllers.
- Persistence is an implementation detail, not the place where business policy lives.

## Recommended package structure

Prefer package-by-feature, then layer within the feature.

```text
com.manhattan.banku
  wallet
    controller
    service
    domain
    repository
    dto
    mapper
    exception
    config
```

If the feature stays in-memory for now, keep the same shape but omit persistence packages until needed.

## Coding style

- Use package-by-feature and keep each feature self-contained.
- Use constructor injection.
- Use records for request/response DTOs and simple immutable values.
- Keep classes small and methods named after business actions.
- Prefer explicit names like `deposit`, `withdraw`, and `transfer` over generic CRUD names.
- Validate at the boundary, not deep in the service.
- Use `long` cents for money; never use floating-point math.
- Avoid mutable shared state unless the phase explicitly needs it.
- Keep controllers thin and deterministic.
- Put `@Transactional` on service methods that change state.
- Use one stable error contract, preferably `ProblemDetail`, for HTTP failures.
- Keep mapping code separate so DTOs do not leak into the domain.

## MVC layer rules

### Controller layer

- Accept the HTTP request, validate the DTO, and call one service method.
- Do not write money rules in the controller.
- Do not call multiple services unless the use case truly requires orchestration.
- Translate service results into response DTOs.
- Return the correct HTTP status code and a stable error body.

### Service layer

- Own the business rule.
- Decide whether a request is valid for the wallet system.
- Coordinate state changes atomically.
- Keep transaction boundaries here when state changes are involved.
- Throw clear business exceptions for invalid operations.

### Domain layer

- Keep wallet and money invariants here.
- Prefer immutability.
- Keep the domain free from controller and persistence concerns.
- Use the domain model to describe the business, not the transport layer.

### Repository / persistence layer

- Store and retrieve data only.
- Do not encode business policy inside repository methods.
- Keep JPA entities separate from API DTOs.
- Map between persistence models and domain models explicitly.

## Core business system reminders

Before changing code, check these questions:

1. Does this change affect wallet balance, transfer, or validation?
2. Could this create a partial update or a hidden side effect?
3. What happens when the operation fails halfway through?
4. What is the business-facing error message or status code?
5. Which test proves the behavior before and after the change?

If the answer touches money, treat it as a core business change, not a UI concern.

## Validation and errors

- Validate required fields at the request boundary.
- Reject blank ids and non-positive amounts early.
- Use clear business exceptions for duplicate wallet, missing wallet, overdraft, and self-transfer.
- Keep the error format stable across controller and integration tests.
- Prefer `ProblemDetail` or one error DTO shape for all failures.

## Test order

1. Domain tests.
2. Service tests.
3. Controller tests.
4. Integration tests.
5. Bruno contract tests.

## Coding checklist for agents

Before coding, write down:

- the use case,
- the input fields,
- the output shape,
- the failure cases,
- the transaction boundary,
- the test that proves the rule.

## What to avoid

- Business logic in controllers.
- Returning persistence entities directly from endpoints.
- Duplicating the same validation in multiple layers.
- Floating-point money math.
- Generic helper methods that hide business intent.
- Adding new framework complexity before the business flow is stable.
- Leaving TODOs where a test or a rule should exist.

## System logic summary

- A wallet starts at zero.
- Deposits add funds.
- Withdrawals subtract only when funds exist.
- Transfers debit and credit atomically.
- Duplicate wallet ids are rejected.
- Blank ids and non-positive amounts are rejected.
- Failures must not leave partial state.
