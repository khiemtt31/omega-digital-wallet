# Validation notes — Phase 01

## What was validated

- Phase 01 should act as a core domain contract, not a generic roadmap note.
- Ledger, transaction history, idempotency, persistence, and HTTP are better treated as later-phase concerns unless the team explicitly promotes them into scope.
- Bruno belongs in the validation plan and agent skills, not inside the domain spec itself.
- The preferred Spring Boot style for this repo is domain-first layered or hexagonal-lite, not plain JPA + MVC.

## Decisions captured

- Money stays in cents as `long`.
- Transfers must be atomic.
- Domain code stays framework-free.
- HTTP failures should use one stable error shape, preferably `ProblemDetail`.
- JUnit covers the domain and service rules first; Bruno covers the HTTP contract later.

## Open questions

- Exact REST endpoint names for the future API slice.
- Final Bruno collection root path.
- Whether transaction history belongs to Phase 02 or Phase 03.

## Handoff to the coding agent

Implement Phase 01 from `docs/phase-01.md` and `docs/plans/validates-01.md`.

- Keep the core model small.
- Do not add persistence or HTTP behavior yet.
- Keep business rules explicit and test-first.
