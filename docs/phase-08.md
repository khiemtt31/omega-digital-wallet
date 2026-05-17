# Phase 08 — Advanced learning

## Outcome

Practice distributed-system patterns.

## Use cases

- Publish async events.
- Add realtime updates.
- Learn compensation flows.

## User stories

- As a learner, I want to see event-driven architecture in action.
- As a learner, I want to understand how async systems stay consistent.

## Functional requirements

- Add Redis caching.
- Add Kafka events.
- Add WebSocket or SSE updates.
- Add saga-based transaction flows.
- Add notifications.

## Non-functional requirements

- Event handling must be retry-safe.
- The system must tolerate eventual consistency.
- Logs and traces should help debugging.
- Core wallet logic must remain correct.

## Executable code tasks

- Add `WalletDomainEvent` and `LedgerEntryCreatedEvent` classes.
- Add a Kafka producer for wallet events.
- Add a Kafka consumer that updates a read model or notification handler.
- Add an SSE or WebSocket endpoint for realtime wallet updates.
- Add integration tests for publish/consume and compensation flow.

## Done when

- Async event handling works without breaking the core wallet flow.

## Reference docs

- [Backend coding style and core business reminders](./agent-skills/llm-coding-style-backend.md)
