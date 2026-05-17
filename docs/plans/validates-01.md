# Phase 01 validation plan

## Purpose

Turn Phase 01 into an executable runbook for an implementation agent and a future Bruno runner.

## Scope

- Validate core wallet behavior first through unit and service tests.
- Describe future Bruno REST scenarios for the same use cases.
- Keep ledger, persistence, and idempotency out of this phase unless they are intentionally added.

## Preconditions

- `docs/phase-01.md` is approved.
- `docs/agent-skills/llm-coding-style.md` and `docs/agent-skills/bruno-testing.md` are available.
- The HTTP status-code and error-body policy is fixed before Bruno files are written.

## Execution order

1. Write failing tests for the wallet rules.
2. Implement the minimal in-memory behavior.
3. Re-run tests until green.
4. Capture HTTP scenarios in Bruno when controllers exist.
5. Review the failure matrix for atomicity and consistency.

## Status and error policy

- `201 Created` for create wallet.
- `200 OK` for read, deposit, withdraw, and transfer success.
- `400 Bad Request` for malformed input and blank ids.
- `404 Not Found` for missing wallets.
- `409 Conflict` for duplicate wallet creation, overdraft, and self-transfer.
- Use one stable error body for all failures, preferably `ProblemDetail`.

## Scenario matrix

| ID | Scenario | Preconditions | Request / action | Expected result | Verification layer | Bruno path (suggested) |
| --- | --- | --- | --- | --- | --- | --- |
| V-01 | Create wallet | none | `POST /wallets` with a new id | `201`, wallet id, balance `0` | JUnit now, Bruno later | `bruno/wallet/create-wallet.bru` |
| V-02 | Duplicate wallet | wallet already exists | repeat `POST /wallets` with same id | `409`, no extra wallet created | JUnit now, Bruno later | `bruno/wallet/errors/duplicate-wallet.bru` |
| V-03 | Blank wallet id | none | `POST /wallets` with blank id | `400`, request rejected | JUnit now, Bruno later | `bruno/wallet/errors/blank-wallet-id.bru` |
| V-04 | Deposit success | wallet exists | `POST /wallets/{id}/deposits` with a positive amount | `200`, balance increases | JUnit now, Bruno later | `bruno/wallet/deposit.bru` |
| V-05 | Deposit invalid amount | wallet exists | deposit `0` or a negative amount | `400`, balance unchanged | JUnit now, Bruno later | `bruno/wallet/errors/deposit-invalid-amount.bru` |
| V-06 | Withdraw success | wallet exists with enough funds | `POST /wallets/{id}/withdrawals` with a positive amount | `200`, balance decreases | JUnit now, Bruno later | `bruno/wallet/withdraw.bru` |
| V-07 | Withdraw overdraft | wallet exists with insufficient funds | withdraw more than the balance | `409`, balance unchanged | JUnit now, Bruno later | `bruno/wallet/errors/withdraw-overdraft.bru` |
| V-08 | Transfer success | source and destination wallets exist | `POST /wallets/transfer` with a positive amount | `200`, source decreases, destination increases | JUnit now, Bruno later | `bruno/wallet/transfer.bru` |
| V-09 | Self-transfer | one wallet exists | transfer from the same wallet to itself | `409`, balance unchanged | JUnit now, Bruno later | `bruno/wallet/errors/self-transfer.bru` |
| V-10 | Missing source wallet | destination exists only | transfer from a missing wallet | `404`, destination unchanged | JUnit now, Bruno later | `bruno/wallet/errors/missing-source-wallet.bru` |
| V-11 | Missing destination wallet | source exists only | transfer to a missing wallet | `404`, source unchanged | JUnit now, Bruno later | `bruno/wallet/errors/missing-destination-wallet.bru` |
| V-12 | Atomic rollback | source and destination exist | force a failed transfer | failure leaves both balances unchanged | JUnit now, Bruno later | `bruno/wallet/errors/transfer-atomicity.bru` |
| V-13 | Future idempotent replay | request id tracking exists | repeat the same request id | same response, no duplicate charge | blocked until later phase | `bruno/wallet/future/idempotency-replay.bru` |

## Exit criteria

- Every row in the matrix is either implemented or explicitly marked blocked.
- The wallet core passes all unit and service tests.
- The Bruno collection paths are named before the API slice is implemented.
- No scenario depends on hidden state or manual intervention.
