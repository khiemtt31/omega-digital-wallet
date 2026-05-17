# Phase 00 — Scope and setup

## Outcome

A clear project shape and working local setup.

## Use cases

- Decide what the app will and will not do.
- Prepare a local environment that can run the backend and frontend.

## User stories

- As a learner, I want the scope fixed before coding.
- As a learner, I want one command to start local dependencies.

## Functional requirements

- Define wallet, transaction, ledger, and idempotency boundaries.
- Confirm Spring Boot backend and Angular frontend as the learning stack.
- Write the first API contract for wallet actions.
- Prepare local dev infrastructure with Docker Compose.

## Non-functional requirements

- Setup must be reproducible on a new machine.
- Folder names must stay consistent and simple.
- The initial design must stay small enough to finish.

## Executable code tasks

- Scaffold backend package folders for controller, service, domain, and infra.
- Create the Angular workspace and app shell.
- Add or update Docker Compose for PostgreSQL, Redis, and Kafka.
- Add local environment templates for backend and frontend config.

## Done when

- The stack is fixed.
- The first API slice is defined.
- The project runs locally without guesswork.
