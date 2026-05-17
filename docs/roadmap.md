# Digital Wallet Learning Project Roadmap

This is the overview file for the learning slice of the broader project. It intentionally narrows the repo vision into a buildable Spring Boot + Angular + Stripe test mode path so each phase is executable.

## Project shape

- Backend: Spring Boot
- Frontend: Angular
- Payments: Stripe test mode only
- Learning style: one phase = one small slice

## Current starting point

- The repo already has a Spring Boot backend module with wallet logic.
- Angular is not present yet.
- Stripe is not integrated yet.

## Guiding rules

- Use the ledger as the source of truth once persistence and transaction history are in scope.
- Introduce `request_id` only when idempotency is part of the current phase.
- Every public endpoint should have validation and clear error responses.
- Build one small vertical slice at a time.

## Phase files

| File | Focus |
| --- | --- |
| [phase-00.md](./phase-00.md) | Scope and setup |
| [phase-01.md](./phase-01.md) | Core domain model |
| [phase-02.md](./phase-02.md) | Backend REST API |
| [phase-03.md](./phase-03.md) | Database persistence |
| [phase-04.md](./phase-04.md) | Angular frontend |
| [phase-05.md](./phase-05.md) | Stripe test mode |
| [phase-06.md](./phase-06.md) | Security |
| [phase-07.md](./phase-07.md) | Testing and quality |
| [phase-08.md](./phase-08.md) | Advanced learning |

## Support docs

| File | Focus |
| --- | --- |
| [agent-skills/README.md](./agent-skills/README.md) | Reusable coding and testing rules for agents |
| [plans/README.md](./plans/README.md) | One-off execution plans |
| [conversations/validates-01.md](./conversations/validates-01.md) | Validation notes, decisions, and open questions |

## How to use this roadmap

1. Read the overview.
2. Open the phase file you want to work on.
3. Open the matching support docs in `agent-skills` or `plans` when the phase references them.
4. Turn that phase into small implementation tickets.
5. Keep the phase order unless a later task is blocked.

## Cost note

This project can stay free if you use Stripe test mode only, local infrastructure via Docker, and open-source backend/frontend frameworks.
