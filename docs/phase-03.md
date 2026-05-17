# Phase 03 — Database persistence

## Outcome

Data survives restarts.

## Use cases

- Store wallet and ledger data in PostgreSQL.
- Rebuild balances from stored ledger entries.
- Restart the app without losing data.

## User stories

- As a user, I want my wallet data to survive app restarts.
- As a learner, I want to see how ledger storage works in a real database.

## Functional requirements

- Add PostgreSQL schema and migrations.
- Add JPA entities and repositories.
- Persist ledger entries in the database.
- Derive balance from the ledger.

## Non-functional requirements

- Writes must be transaction-safe.
- Schema changes must be versioned.
- Queries must stay simple enough to reason about.
- No silent data loss is allowed.

## Executable code tasks

- Create `WalletEntity`, `LedgerEntryEntity`, and repository classes.
- Add migration scripts for wallet and ledger tables.
- Implement a DB-backed wallet service that writes ledger entries.
- Add integration tests that verify balance is derived from persisted ledger rows.

## Done when

- Wallet data is no longer only in memory.
- Balance is calculated from stored entries.
