# Phase 05 — Stripe test mode

## Outcome

External payment flows are simulated safely.

## Use cases

- Simulate a payment with fake cards.
- Receive payment events from Stripe.
- Confirm or fail a payment flow.

## User stories

- As a learner, I want to test payment flows without real money.
- As a learner, I want webhook events to update the wallet flow.

## Functional requirements

- Add Stripe test keys.
- Add payment intent creation.
- Add a webhook endpoint.
- Verify webhook signatures.
- Handle success and failure events.

## Non-functional requirements

- No real card data must be used.
- Secret keys must stay out of source control.
- Webhook handling must be idempotent.
- Event processing must be safe to retry.

## Executable code tasks

- Configure the Stripe SDK with test keys and local environment variables.
- Add payment intent creation and confirmation endpoints.
- Implement the webhook controller with signature verification.
- Add tests for Stripe success, failure, and duplicate webhook delivery.
- Test the flow with Stripe test cards and record the expected responses.

## Done when

- A full fake payment flow works end to end.
- No real charges are created.

## Reference docs

- [Backend coding style and core business reminders](./agent-skills/llm-coding-style-backend.md)
- [Bruno testing guide](./agent-skills/bruno-testing.md)
