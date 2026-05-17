# Phase 02 — Backend REST API

## Outcome

Angular can talk to the backend.

## Use cases

- Create and view wallets over HTTP.
- Move money using API calls.
- Read transaction history.

## User stories

- As a client, I want to call wallet actions from HTTP endpoints.
- As a client, I want clear validation errors when I send bad input.

## Functional requirements

- Add wallet creation, get-wallet, deposit, withdraw, and transfer endpoints.
- Add transaction history endpoint.
- Add request validation and error responses.
- Add OpenAPI/Swagger documentation.

## Non-functional requirements

- Endpoints must be RESTful and stateless.
- Status codes must be consistent.
- Validation messages must be clear.
- Controllers must stay thin and delegate to services.

## Executable code tasks

- Create `WalletController` with create/get/deposit/withdraw/transfer methods.
- Add request and response DTOs for each wallet endpoint.
- Add global exception handling for validation and business errors.
- Add MockMvc integration tests for one happy path and one validation failure per endpoint group.
- Add Swagger/OpenAPI setup and expose the API contract.

## Done when

- The API can be called from Postman or curl.
- All wallet actions are exposed as REST endpoints.

## Reference docs

- [Backend coding style and core business reminders](./agent-skills/llm-coding-style-backend.md)
- [Bruno testing guide](./agent-skills/bruno-testing.md)
