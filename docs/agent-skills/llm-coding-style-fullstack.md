# Fullstack coding style and cross-layer coordination

## Purpose

Keep Angular + Spring Boot slices small, synchronized, and easy to verify.

Use this file when one user flow spans both `banko/` and `banku/`. If the change is mostly frontend, use the frontend style doc. If it is mostly backend, use the backend style doc.

## Project mental model

- One user action should map to one backend use case, one API contract, and one UI state machine.
- The backend stays the source of truth for business rules.
- The frontend owns presentation, feedback, and navigation.
- Shared names should stay aligned across layers: create, deposit, withdraw, transfer, wallet, history.

## Cross-layer rules

- Define the API contract before coding the UI.
- Keep request DTOs, response DTOs, and UI view models explicit.
- Do not leak backend entities into Angular components.
- Do not duplicate backend business validation in the frontend beyond basic UX checks.
- Keep loading, empty, success, and error states consistent with backend outcomes.
- Keep error shapes stable so the UI can map them predictably.
- Keep auth split clearly: the client attaches tokens, the server authorizes requests.
- Route guards and interceptors are UX helpers, not security boundaries.
- If the slice changes both layers, update both in the same pass.

## Contract-first workflow

1. Write the user flow and success path.
2. Define request, response, and error shapes.
3. Implement or update the backend use case.
4. Implement the Angular service and UI flow.
5. Add tests at each layer that proves the contract.

## Testing order

1. Backend domain and service tests.
2. Backend controller or integration tests.
3. Frontend service and component tests.
4. Cross-layer contract or API tests.
5. End-to-end tests for the main user path.

## Coding checklist for agents

Before coding, write down:

- the user journey,
- the backend use case,
- the API contract,
- the UI state transitions,
- the auth or error behavior,
- the tests that prove the slice end to end.

## What to avoid

- Updating only one layer and leaving the contract inconsistent.
- Duplicating business rules in the UI and backend without a clear reason.
- Returning different error shapes for the same failure.
- Hiding state transitions in a component or controller.
- Shipping a UI flow without a matching backend test.

## Cross-layer summary

- Backend rules decide truth.
- Frontend rules decide usability.
- The contract keeps both sides honest.
- Tests should prove the same flow at the right layer.
