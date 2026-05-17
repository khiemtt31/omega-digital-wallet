# Phase 07 — Testing and quality

## Outcome

The project is reliable and easy to explain.

## Use cases

- Verify that money rules still work after changes.
- Prevent regressions while refactoring.

## User stories

- As a learner, I want tests to catch mistakes early.
- As a learner, I want the project to be easy to run and explain.

## Functional requirements

- Add unit tests for wallet rules.
- Add integration tests for the API.
- Add frontend component tests.
- Add end-to-end tests for one happy path.
- Keep project documentation current.

## Non-functional requirements

- Tests should be deterministic.
- Test runs should stay reasonably fast.
- Failures should be easy to read.
- Coverage should focus on core flows first.

## Executable code tasks

- Add service and controller tests for create, deposit, withdraw, and transfer flows.
- Add database integration tests for persistence and rollback behavior.
- Add Angular component tests for wallet forms and history display.
- Add one end-to-end user flow from login to transfer.
- Update docs with run instructions and test commands.

## Done when

- Main flows are covered by tests.
- Another person can run the project from the docs.
