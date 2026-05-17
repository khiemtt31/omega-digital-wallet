# Bruno testing guide

## Purpose

Use Bruno to verify HTTP contracts for wallet use cases once REST endpoints exist.

## What Bruno is for

- status codes,
- request and response shape,
- validation errors,
- business-rule failures,
- repeated requests when idempotency is added later.

## Collection rules

- One folder per feature.
- One request per scenario.
- Keep request names human-readable.
- Assert status, body shape, and key business values.
- Keep tests independent; do not rely on run order.
- Use setup or fixture requests instead of manually editing state.

## Scenario set

- create wallet happy path.
- duplicate wallet.
- blank wallet id.
- deposit success and invalid amount.
- withdraw success and overdraft.
- transfer success, self-transfer, missing source wallet, and missing destination wallet.
- atomicity after failed transfer.
- future: replay request if idempotency is added.

## Suggested assertions

- `201 Created` for wallet creation.
- `200 OK` for read, deposit, withdraw, and transfer success.
- `400 Bad Request` for malformed input or blank ids.
- `404 Not Found` for missing wallets.
- `409 Conflict` for duplicate wallet creation and business-rule conflicts.
- Success responses should include the wallet id and cents balance.
- Error responses should use a stable, machine-readable shape, preferably `ProblemDetail`.

## Suggested structure

- `bruno/wallet/create-wallet.bru`
- `bruno/wallet/deposit.bru`
- `bruno/wallet/withdraw.bru`
- `bruno/wallet/transfer.bru`
- `bruno/wallet/errors/*.bru`

## Notes

Bruno validates the HTTP contract, not the domain math. Keep the domain tests in JUnit and use Bruno only after the API slice exists.
