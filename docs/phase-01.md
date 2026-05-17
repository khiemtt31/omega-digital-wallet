# Phase 01 — Core domain model

## Outcome

The wallet core can run in memory with strict money rules, deterministic behavior, and no dependency on HTTP, persistence, or security.

## Use cases

### In scope

- Create a wallet.
- Read a wallet balance.
- Deposit money into a wallet.
- Withdraw money from a wallet.
- Transfer money between wallets.

### Deferred / next phase

- Expose those operations over REST.
- Read transaction history.
- Persist state in PostgreSQL.
- Add request idempotency and ledger/audit projection.

### Use case to test map

| Use case | What must be true | Primary verification |
| --- | --- | --- |
| Create wallet | A new wallet starts at zero and duplicate ids fail | Unit test now, Bruno later |
| Read wallet balance | The stored balance is returned exactly | Unit test now, Bruno later |
| Deposit money | Only positive amounts are accepted and balance increases | Unit test now, Bruno later |
| Withdraw money | Only positive amounts are accepted and overdrafts fail | Unit test now, Bruno later |
| Transfer money | Both wallets update together or not at all | Unit test now, Bruno later |

## User stories

- As a user, I want to create a wallet that starts at zero so I can use it immediately.
- As a user, I want deposits and withdrawals to follow clear money rules so I never get a silent balance bug.
- As a user, I want transfers to be atomic so money cannot disappear or duplicate.
- As an implementation agent, I want the phase document to tell me what to build, what to test, and what to defer.

## Target slice map

Use this as the mental image for the core wallet flow and the later HTTP/persistence slices.

| Area | Phase 01 shape | Later slice |
| --- | --- | --- |
| Domain entity / value object | `Wallet` with `id` and `balanceInCents` | Persisted as `WalletEntity` in Phase 03 |
| Application service | `WalletService` and `InMemoryWalletService` | Backed by REST in Phase 02 |
| Request DTOs | not implemented yet | `CreateWalletRequest`, `MoneyRequest`, `TransferRequest` |
| Response DTOs | not implemented yet | `WalletResponse`, `BalanceResponse`, stable `ErrorResponse` / `ProblemDetail` |
| API endpoints | not implemented yet | `POST /wallets`, `GET /wallets/{id}`, `POST /wallets/{id}/deposits`, `POST /wallets/{id}/withdrawals`, `POST /wallets/transfer` |
| Persistence entities | not implemented yet | `WalletEntity`, `LedgerEntryEntity` in Phase 03 |

The goal is to keep Phase 01 focused on the domain and service shape while still showing the future DTO and API naming that the skills docs will reinforce.

## Functional requirements

### Domain model

- `Wallet` is a record or immutable value object with `id` and `balanceInCents`.
- Wallet ids are required and must not be blank.
- Money is stored in minor units only.
- The current in-memory service is the executable boundary for Phase 01.
- `LedgerEntry`, `Transaction`, and request id tracking are not part of this phase.

### Wallet operations

- `createWallet` creates a zero-balance wallet and rejects duplicates.
- `getWallet` returns an existing wallet or fails clearly when the wallet does not exist.
- `deposit` accepts only positive amounts and adds the amount to the wallet balance.
- `withdraw` accepts only positive amounts and rejects overdrafts.
- `transfer` accepts only positive amounts, rejects self-transfer, and updates both wallets atomically.

### Error rules

- Blank wallet ids fail fast.
- Zero or negative amounts fail fast.
- Missing wallets fail with a clear business error.
- Duplicate wallet creation fails with a clear business error.
- Failed transfers must leave both wallets unchanged.

### Validation targets for Bruno

- When Phase 02 exposes these use cases over HTTP, each operation must have one happy path and one failure scenario in Bruno.
- API validation must cover duplicate wallet, blank id, invalid amount, overdraft, self-transfer, missing source wallet, missing destination wallet, and atomic rollback.

## Non-functional requirements

- Balance math must use `long` cents only.
- Behavior must be deterministic and testable.
- The core domain must stay free of Spring, JPA, and web annotations.
- The in-memory service must behave safely under concurrent access.
- Error messages and status mapping must stay consistent across tests and later HTTP adapters.
- Keep the design small enough to implement in one phase without guessing.

## Executable code tasks

1. Write or update unit tests for create, get, deposit, withdraw, and transfer before changing the implementation.
2. Keep `Wallet` minimal and immutable.
3. Implement `WalletService` behavior in `InMemoryWalletService` with explicit validation and atomic transfer logic.
4. Add failure-path tests for blank wallet ids, duplicate wallets, invalid amounts, missing wallets, overdrafts, and self-transfer.
5. Capture the HTTP/Bruno validation matrix in `docs/plans/validates-01.md`.
6. Keep the coding rules in `docs/agent-skills/llm-coding-style-backend.md` and `docs/agent-skills/bruno-testing.md` aligned with the phase.

## Definition of done

- Every in-scope wallet operation is covered by tests.
- All failure paths fail clearly and consistently.
- Transfer does not produce partial state.
- The phase is executable by a coding agent without reading unrelated docs.
- Bruno scenarios exist in the validation plan for the future HTTP slice.

## Reference docs

- [Phase 01 validation plan](./plans/validates-01.md)
- [Backend coding style and core business reminders](./agent-skills/llm-coding-style-backend.md)
- [Bruno testing guide](./agent-skills/bruno-testing.md)
